package com.nardo.platform.business.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    private String productID;
    private String name;
    private String description;
    private double price;
    private int currentQuantity;
    private int safetyThreshold;
    private int softLockedQuantity;
    private LocalDateTime lockTimeStamp;
    private boolean isAvailable = true;

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

    public boolean applySoftLock(int qty) {
        if ((currentQuantity - softLockedQuantity) >= qty) {
            this.softLockedQuantity += qty;
            this.lockTimeStamp = LocalDateTime.now();
            return true;
        }
        return false;
    }

    public void deductStock(int qty) {
        this.currentQuantity -= qty;
        this.softLockedQuantity -= qty;
    }

    public void releaseLock(int qty) {
        this.softLockedQuantity -= qty;
    }

    public boolean isBelowThreshold() {
        return this.currentQuantity <= this.safetyThreshold;
    }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }

    public String getProductID() {return productID;}
    public String getId() {return productID;}
    public String getName() {return name;}
    public void setName(String name) { this.name = name; }
    public double getPrice() {return price;}
    public void setPrice(double price) { this.price = price; }
    public String getDescription() {return description;}
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getLockTimeStamp() { return lockTimeStamp; }
    public int getSoftLockedQuantity() { return softLockedQuantity; }
    public int getSafetyThreshold() { return safetyThreshold; }
    public int getCurrentQuantity() {return currentQuantity;}
    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }
}
