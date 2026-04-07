package com.nardo.platform.business.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Payment {

    @Id
    private String paymentID;
    private String gatewayTransactionID; 
    private double amountPaid;
    private LocalDateTime timestamp;
    private String status;

    public Payment() {}

    public Payment(String paymentID, String transactionID, double amountPaid) { 
        this.paymentID = paymentID;
        this.gatewayTransactionID = transactionID;
        this.amountPaid = amountPaid;
        this.timestamp = LocalDateTime.now();
        this.status = "SUCCESS"; 
    }

    public boolean isValidForAmount(double expectedAmount) {
        return this.amountPaid >= expectedAmount && "SUCCESS".equals(this.status);
    }

    public String getPaymentID() {return paymentID;}
    public String getGatewayTransactionID() {return gatewayTransactionID;}
    public double getAmountPaid() {return amountPaid;}
    public double getAmount() {return amountPaid;}
    public String getStatus() {return status;}
    public LocalDateTime getTimestamp() { return timestamp; }
}
