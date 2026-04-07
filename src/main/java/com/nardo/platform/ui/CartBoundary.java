package com.nardo.platform.ui;

import com.nardo.platform.business.control.CartController;
import com.nardo.platform.business.entity.Cart;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartBoundary {

    private final CartController cartController;

    @Autowired
    public CartBoundary(CartController cartController) {
        this.cartController = cartController;
    }

    private Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = cartController.initializeCart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        model.addAttribute("cart", getCart(session));
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam String productID, @RequestParam int qty, HttpSession session) {
        Cart cart = getCart(session);
        cartController.addItemToCart(cart, productID, qty);
        return "redirect:/order/menu";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam String productID, @RequestParam int qty, HttpSession session) {
        Cart cart = getCart(session);
        cartController.updateCartItemQuantity(cart, productID, qty);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam String productID, HttpSession session) {
        Cart cart = getCart(session);
        cartController.removeItemFromCart(cart, productID);
        return "redirect:/cart";
    }
}

