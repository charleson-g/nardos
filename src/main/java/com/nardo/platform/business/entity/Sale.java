package com.nardo.platform.business.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Sale {

    @Id
    private String saleID;
    private String productID;
    private int quantity;
    private double totalAmount;
    private LocalDateTime timestamp;

    private String syncStatus; 

    public Sale() {}

    public Sale(String saleID, String productID, int quantity, double totalAmount){
        this.saleID = saleID;
        this.productID = productID;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.timestamp = LocalDateTime.now();
        this.syncStatus = "PENDING"; 
    }

    public String getSaleID(){return saleID;}
    public String getSyncStatus(){return syncStatus;}
    public void setSyncStatus(String syncStatus){this.syncStatus = syncStatus;}
    public String getProductID() { return productID; }
    public int getQuantity() { return quantity; }
    public double getTotalAmount() { return totalAmount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void markAsSynced() { this.syncStatus = "SYNCED"; }
}
