package com.nardo.platform.business.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_orders")
public class Order {

    @Id
    private String orderId;
    private String customerName;
    private LocalDateTime orderTimestamp;
    private LocalDateTime pickupTime;
    private String status;
    private double totalAmount;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<OrderItem> items = new java.util.ArrayList<>();

    public Order() {}

    public Order(String orderId, LocalDateTime pickupTime, double amount) {
        this.orderId = orderId;
        this.pickupTime = pickupTime;
        this.totalAmount = amount;
        this.orderTimestamp = LocalDateTime.now();
        this.status = "PENDING";
    }

    public void markAsPaid(Payment payment) {
        this.payment = payment;
        this.status = "PAID";
    }

    public void markAsReady() { this.status = "READY"; }
    public void markAsCollected() { this.status = "COLLECTED"; }

    public void setStatus(String newStatus) { this.status = newStatus; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setPayment(Payment payment) { this.payment = payment; }

    public String getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public LocalDateTime getPickupTime() { return pickupTime; }
    public double getTotalAmount() { return totalAmount; }
    public Payment getPayment() { return payment; }
    public LocalDateTime getOrderTimestamp() { return orderTimestamp; }
    
    public String getCustomerName() { return customerName == null ? "Customer" : customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public java.util.List<OrderItem> getItems() { return items; }
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}
