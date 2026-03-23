package com.nardo.platform.business.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;


/**
 * Entity class representing a food or drink item in Nardo's Shop.
 * This class handles the "Soft Lock"and threshold logic.
 */

@Entity


public class Product {

    @Id
    private String productID;
    private String name;
    private String description;
    private double price;
    private int currentQuantity;
    private int safetyThreshold;

    // Tracks stock temporarily reserved during the 5-minute checkout window
    private int softLockedQuantity;
    private LocalDateTime lockTimeStamp;

    // Default Constructor for JPA
    public Product() {}

    public Product(String productID, String name, String description, double price, int qty, int threshold) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.currentQuantity = qty;
        this.safetyThreshold = threshold;
        this.softLockedQuantity = 0;
    }

    /**
     * Temporarily reserves stock for 5 minutes  during checkout.
     * Prevents "no-show" losses and ensures inventory availability.
     */

    public boolean applySoftLock(int qty) {
        if ((currentQuantity - softLockedQuantity) >= qty) {
            this.softLockedQuantity += qty;
            this.lockTimeStamp = LocalDateTime.now();
            return true;
        }
        return false;
    }

    /**
     * Permanently deducts stock after a successful "Sure Money Payment"
     */
    public void deductStock(int qty) {
        this.currentQuantity -= qty;
        this.softLockedQuantity -= qty; // Convert soft lock to permanent deduction
    }

    /**
     * Releases the soft lock if a payment is denied or a gateway timeout occurs
     */
    public void releaseLock(int qty) {
        this.softLockedQuantity -= qty;
    }

    /**
     * Check if the item has fallen below its safety limit
     */
    public boolean isBelowThreshold(int qty) {
        return this.currentQuantity <= this.safetyThreshold;
    }

    // Getters and Setters
    public String getProductID() {return productID;}
    public String getName() {return name;}
    public double getPrice() {return price;}
    public String getDescription() {return description;}
    public int getSoftLockedQuantity() { return softLockedQuantity; }
    public int getSafetyThreshold() { return safetyThreshold; }
    public int getCurrentQuantity() {return currentQuantity;}
    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }


}
