package com.nardo.platform.business.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/**
 * Maintain an immutable digital history of all inventory transactions.
 * Captures Metadata automatically without user input.
 */

@Entity
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

    private String productID;
    private int quantityChanged; // e.g., -5 for a sale, +20 for delivery
    private String movementType; // e.g., "SALE", "DELIVERY", "ADJUSTMENT"
    private String username; // The Admin/Staff responsible
    private LocalDateTime timestamp;
    private String supplier; // e.g., "Massy"

    public StockMovement(){}

    public StockMovement(String productID, int quantityChanged, String movementType, String username){
        this.productID = productID;
        this.quantityChanged =  quantityChanged;
        this.movementType = movementType;
        this.username = username;
        this.timestamp = LocalDateTime.now();
    }

    public StockMovement(String productID, int quantityChanged, String movementType, String username, String supplier){
        this.productID = productID;
        this.quantityChanged =  quantityChanged;
        this.movementType = movementType;
        this.username = username;
        this.supplier = supplier;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getProductID(){return productID;}
    public int getQuantityChanged(){return quantityChanged;}
    public LocalDateTime getTimestamp(){return timestamp;}
    public String getMovementType() { return movementType; }
    public String getUsername() { return username; }
    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
}
