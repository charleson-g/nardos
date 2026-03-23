package com.nardo.platform.business.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class representing the financial transaction.
 * Satisfies the "Sure Money" rule by providing a verifiable record of payment.
 */

@Entity

public class Payment {

    @Id
    private String paymentID;
    private String gatewayTransactionID; // ID return by Stripe,etc
    private double amountPaid;
    private LocalDateTime timestamp;
    private String status; // e.g., "SUCCESS", "DECLINED", "REFUNDED"

    // Default Constructor for JPA
    public Payment() {}

    public Payment(String paymentID, String transactionID, double amountPaid) {
        this.paymentID = paymentID;
        this.gatewayTransactionID = transactionID;
        this.amountPaid = amountPaid;
        this.timestamp = LocalDateTime.now();
        this.status = "SUCCESS"; // Typically created only upon successful capture
    }

    /**
     * Logic for verifying if the payment meets the order's total.
     */
    public boolean isValidForAmount(double expectedAmount) {
        return this.amountPaid >= expectedAmount && "Success".equals(this.status);
    }

    // Getters
    public String getPaymentID() {return paymentID;}
    public String getGatewayTransactionID() {return gatewayTransactionID;}
    public double getAmountPaid() {return amountPaid;}
    public String getStatus() {return status;}
}
