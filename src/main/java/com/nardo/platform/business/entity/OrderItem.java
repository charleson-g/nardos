package com.nardo.platform.business.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private double priceAtTimeOfOrder;

    public OrderItem() {}

    public OrderItem(Order order, Product product, int quantity, double priceAtTimeOfOrder) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.priceAtTimeOfOrder = priceAtTimeOfOrder;
    }

    public Long getId() { return id; }
    public Order getOrder() { return order; }
    public Product getProduct() { return product; }
    public String getProductId() { return product != null ? product.getProductID() : null; }
    public int getQuantity() { return quantity; }
    public double getPriceAtTimeOfOrder() { return priceAtTimeOfOrder; }

    public void setOrder(Order order) { this.order = order; }
}
