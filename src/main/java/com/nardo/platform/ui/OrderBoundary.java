package com.nardo.platform.ui;

import com.nardo.platform.business.control.OrderController;
import com.nardo.platform.business.entity.Cart;
import com.nardo.platform.business.entity.Product;
import com.nardo.platform.business.entity.Order;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderBoundary {

    private final OrderController orderController;

    @Autowired
    public OrderBoundary(OrderController orderController) {
        this.orderController = orderController;
    }

    private Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @GetMapping("/menu")
    public String displayMenu(Model model) {
        List<Product> items = orderController.getAllProducts();
        model.addAttribute("products", items);
        return "menu"; // Points to menu.html
    }

    @GetMapping("/checkout")
    public String showCheckout(HttpSession session, Model model) {
        Cart cart = getCart(session);
        if (cart.getItems().isEmpty()) {
            return "redirect:/order/menu";
        }
        model.addAttribute("cart", cart);
        model.addAttribute("slots", orderController.getAvailableSlots());
        return "order_form";
    }

    @PostMapping("/checkout")
    public String handleCheckout(@RequestParam String pickupTime, HttpSession session, Model model) {
        Cart cart = getCart(session);
        if (cart.getItems().isEmpty()) return "redirect:/order/menu";

        boolean locked = orderController.initiateOrder(cart);

        if (locked) {
            model.addAttribute("cart", cart);
            model.addAttribute("pickupTimeStr", pickupTime);
            return "payment"; 
        }
        return "redirect:/cart?error=OutOfStock";
    }

    @PostMapping("/pay")
    public String capturePaymentDetails(@RequestParam String cardNum, @RequestParam String pickupTimeStr, HttpSession session, Model model) {
        Cart cart = getCart(session);
        if (cart.getItems().isEmpty()) return "redirect:/order/menu";

        Order order = orderController.processPayment(cart, cart.getTotalValue(), cardNum, pickupTimeStr);
        if (order != null) {
            session.removeAttribute("cart");
            model.addAttribute("order", order);
            model.addAttribute("orderId", order.getOrderId());
            return "confirmation";
        }
        return "redirect:/cart?error=PaymentDenied";
    }

    @GetMapping("/track")
    public String trackOrderPage() {
        return "order_tracking_view";
    }

    @GetMapping("/track/{id}")
    public String showOrderStatus(@PathVariable String id, Model model) {
        String status = orderController.checkOrderStatus(id);
        Order order = orderController.getOrder(id);
        model.addAttribute("status", status);
        model.addAttribute("orderId", id);
        model.addAttribute("order", order);
        return "order_tracking_view";
    }

}

