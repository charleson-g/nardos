package com.nardo.platform.business.control;

import com.nardo.platform.business.entity.Cart;
import com.nardo.platform.business.entity.Product;
import com.nardo.platform.persistence.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartController {

    private final InventoryRepository inventoryRepo;

    @Autowired
    public CartController(InventoryRepository inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    public Cart initializeCart() {
        return new Cart();
    }

    public void addItemToCart(Cart cart, String productId, int quantity) {
        Product product = inventoryRepo.findById(productId).orElse(null);
        if (product != null && quantity > 0) {
            cart.addItem(product, quantity);
        }
    }

    public void updateCartItemQuantity(Cart cart, String productId, int quantity) {
        if (cart != null) {
            cart.updateQuantity(productId, quantity);
        }
    }

    public void removeItemFromCart(Cart cart, String productId) {
        if (cart != null) {
            cart.removeItem(productId);
        }
    }
}
