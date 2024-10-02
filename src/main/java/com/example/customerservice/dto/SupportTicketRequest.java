package com.example.customerservice.dto;

public class SupportTicketRequest {
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    private String customerId;
    private String issue;

    // Getters and Setters
}
