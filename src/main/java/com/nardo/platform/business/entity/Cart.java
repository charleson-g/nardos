package com.nardo.platform.business.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getProductID().equals(product.getProductID())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public void updateQuantity(String productId, int quantity) {
        if (quantity <= 0) {
            removeItem(productId);
            return;
        }
        for (CartItem item : items) {
            if (item.getProduct().getProductID().equals(productId)) {
                item.setQuantity(quantity);
                return;
            }
        }
    }

    public void removeItem(String productId) {
        items.removeIf(i -> i.getProduct().getProductID().equals(productId));
    }

    public double getTotalValue() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void clear() {
        items.clear();
    }
    
    public int getTotalQuantity() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }
}
