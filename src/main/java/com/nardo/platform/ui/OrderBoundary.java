package com.nardo.platform.ui;

import com.nardo.platform.business.control.OrderController;
import com.nardo.platform.business.control.PaymentGatewayAdapter;
import com.nardo.platform.business.entity.Product;
import com.nardo.platform.persistence.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Boundary class representing the Web Interface for the Customer.
 * Handles UI navigation and delegates business logic to the OrderController.
 */

@Controller
@RequestMapping("/order")


public class OrderBoundary {

    @Autowired
    private OrderController orderController;

    @Autowired
    private InventoryRepository inventoryRepo; // Used only to fetch data for display

    @Autowired
    private PaymentGatewayAdapter paymentAdapter;

    /**
     * Renders the available menu items for the student
     */
    @GetMapping("/menu")
    public String displayMenu(Model model) {
        List<Product> items = inventoryRepo.findAll();
        model.addAttribute("products", items);
        return "menu"; // Points to menu.html
    }

    /**
     * Captures order selection and initiates the "Soft Lock".
     */
    @PostMapping("/checkout")
    public String handleCheckout(@RequestParam String productID, @RequestParam int qty, Model model) {
        boolean locked = orderController.initiateOrder(productID, qty);

        if (locked) {
            model.addAttribute("productID", productID);
            model.addAttribute("qty", qty);
            return "payment"; // Redirects to the payment entry boundary
        }
        return "redirect:/order/menu?error=OutOfStock";
    }

    /**
     * Handles the secure payment handshake.
     */
    @PostMapping("/pay")
    public String capturePaymentDetails(@RequestParam String productID, @RequestParam int qty, @RequestParam double amount) {
        // Delegate the handshake to the Adapter via the Controller logic
        boolean authorized = paymentAdapter.authorize(amount, "Mock-Card-Details");

                if (authorized) {
                    // Confirm the order and commit inventory deduction
                    orderController.processPaymentSuccess("ORD-" + System.currentTimeMillis(), productID, qty, amount, "TXN-SUCCESS");
                    return "confirmation";
                }
                return "redirect:/order/menu?error=PaymentDenied";
    }

}
