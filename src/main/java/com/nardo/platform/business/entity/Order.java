package com.nardo.platform.business.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class representing a Customer's Online Order.
 * Coordinates the status of the "Sure Money" workflow.
 */

@Entity
@Table(name = "customer_orders")


public class Order {

    @Id
    private String orderId;
    private LocalDateTime orderTimestamp;
    private LocalDateTime pickupTime; // Selected 15-minute slot
    private String status;//e.g., "PENDING", "PAID", "PREPARING", "READY"
    private double totalAmount;

    // Association: Every online order must have exactly one payment record
    @OneToOne
    private Payment payment;

    // Default Constructor for JPA
    public Order() {}

    public Order(String orderId, LocalDateTime pickupTime, double amount) {
        this.orderId = orderId;
        this.pickupTime = pickupTime;
        this.totalAmount = amount;
        this.orderTimestamp = LocalDateTime.now();
        this.status = "PENDING"; // Initial state before payment verification
    }

    /**
     * Finalizes the order state after payment success.
     * This method if invoked by the OrderController after the bank gateway approves.
     */

    public void markAsPaid(Payment payment) {
        this.payment = payment;
        this.status = "PAID";
    }

    /**
     * Transition the order to Nardo's "Preparation "Queue".
     */
    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    // Getters and Setters
    public String getOrderId() {return orderId;}
    public String getStatus() {return status;}
    public LocalDateTime getPickupTime() {return pickupTime;}
    public double getTotalAmount() {return totalAmount;}
    public Payment getPayment() {return payment;}
}
