package com.example.customerservice.model;

import java.util.Date;
import java.util.UUID;

public class SupportTicket {
    private String ticketId;
    private String customerId;
    private String issue;
    private String status;
    private Date createdDate;

    public SupportTicket(String customerId, String issue) {
        this.ticketId = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.issue = issue;
        this.status = "Open";
        this.createdDate = new Date();
    }

    public String getStatus() {
        return status;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public String getTicketId() { return ticketId; }
    public String getCustomerId() { return customerId; }
    public String getIssue() { return issue; }
    public Date getCreatedDate() { return createdDate; }
}
