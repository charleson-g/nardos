package com.nardo.platform.business.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class AlertLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertID;

    private String productID;
    private String message;
    private String type;
    private LocalDateTime alertTime;
    private boolean resolved;
    private String status; 

    public AlertLog() {}

    public AlertLog(String productID) {
        this.productID = productID;
        this.alertTime = LocalDateTime.now();
        this.status = "ACTIVE";
        this.resolved = false;
    }

    public AlertLog(String productID, String message, String type) {
        this.productID = productID;
        this.message = message;
        this.type = type;
        this.alertTime = LocalDateTime.now();
        this.status = "ACTIVE";
        this.resolved = false;
    }

    public Long getAlertID() { return alertID; }
    public String getProductID() { return productID; }
    public String getMessage() { return message; }
    public String getType() { return type; }
    public LocalDateTime getAlertTime() { return alertTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public boolean isResolved() { return resolved; }
    public void setResolved(boolean resolved) { this.resolved = resolved; }
}
