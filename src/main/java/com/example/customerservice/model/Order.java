package com.example.customerservice.model;

// Placeholder class for Order object.
public class Order {
    private String orderId;
    private String details;

    public Order(String orderId, String details) {
        this.orderId = orderId;
        this.details = details;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDetails() {
        return details;
    }
}
