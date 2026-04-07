package com.nardo.platform.ui;

import com.nardo.platform.business.control.*;
import com.nardo.platform.business.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminDashboardBoundary {

    private final ProductUpdateController updateController;
    private final DeliveryController deliveryController;
    private final SalesController salesController;
    private final OrderController orderController;
    private final ReportController reportController;
    private final StockMonitorController stockMonitorController;

    @Autowired
    public AdminDashboardBoundary(ProductUpdateController updateController, 
                                  DeliveryController deliveryController, 
                                  SalesController salesController, 
                                  OrderController orderController,
                                  ReportController reportController,
                                  StockMonitorController stockMonitorController) {
        this.updateController = updateController;
        this.deliveryController = deliveryController;
        this.salesController = salesController;
        this.orderController = orderController;
        this.reportController = reportController;
        this.stockMonitorController = stockMonitorController;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Product> products = updateController.getAllProductsForAdmin();
        long lowStockCount = products.stream().filter(Product::isBelowThreshold).count();

        model.addAttribute("products", products);
        model.addAttribute("orders", orderController.getAllOrders());
        model.addAttribute("alerts", stockMonitorController.getActiveAlerts());
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("lowStockCount", lowStockCount);
        model.addAttribute("totalOrders", orderController.getAllOrders().size());
        
        return "admin_dashboard";
    }

    @GetMapping("/resolve-alert/{id}")
    public String resolveAlert(@PathVariable Long id) {
        stockMonitorController.resolveAlert(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/edit-product/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Product product = updateController.getProductForEdit(id);
        model.addAttribute("product", product);
        return "edit_product_form";
    }

    @PostMapping("/save-product")
    public String handleUpdate(@RequestParam String productID,
                               @RequestParam String name,
                               @RequestParam double price,
                               @RequestParam String description,
                               @RequestParam(defaultValue = "10") int safetyThreshold,
                               @RequestParam(required = false, defaultValue = "false") boolean isAvailable,
                               Model model) {
        
        boolean success = updateController.updateProduct(productID, name, price, description, isAvailable);
        if (success) {
            model.addAttribute("message", "Product updated successfully!");
        } else {
            model.addAttribute("error", "Failed to update product. Check your inputs.");
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/process-delivery")
    public String handleDelivery(@RequestParam String productID,
                                 @RequestParam int quantity,
                                 @RequestParam String supplier,
                                 Model model) {
        boolean success = deliveryController.processDelivery(productID, quantity, supplier);
        if (success) {
            model.addAttribute("message", "Stock Replenished! Ledger updated.");
        } else {
            model.addAttribute("error", "Error: Delivery could not be processed.");
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/record-sale")
    public String handleLocalSale(@RequestParam String productID, @RequestParam int quantity, Model model) {
        boolean success = salesController.recordLocalSale(productID, quantity);
        if (success) {
            model.addAttribute("message", "Sale Successful! Stock updated in Cloud DB.");
        } else {
            model.addAttribute("error", "Error: Insufficient stock or invalid Product ID.");
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/report")
    public String showReport(Model model) {
        StockReport report = reportController.generateSummary("WEEKLY", LocalDateTime.now().minusWeeks(1), LocalDateTime.now());
        model.addAttribute("report", report);
        return "stock_report";
    }

    @PostMapping("/orders/update-status")
    public String handleStatusUpdate(@RequestParam String orderID, @RequestParam String newStatus) {
        orderController.updateStatus(orderID, newStatus);
        return "redirect:/admin/dashboard"; 
    }

    @GetMapping("/inventory")
    public String showInventory(Model model) {
        model.addAttribute("products", orderController.getAllProducts());
        return "inventory_manager";
    }

    @PostMapping("/inventory/add")
    public String handleAddProduct(@RequestParam String productID,
                                   @RequestParam String name,
                                   @RequestParam String description,
                                   @RequestParam double price,
                                   @RequestParam int quantity,
                                   @RequestParam int safetyThreshold) {
        boolean success = updateController.addNewProduct(productID, name, description, price, quantity, safetyThreshold);
        if (success) {
            return "redirect:/admin/inventory?message=Product+added+successfully";
        }
        return "redirect:/admin/inventory?error=Product+ID+already+exists+or+invalid+data";
    }
}
