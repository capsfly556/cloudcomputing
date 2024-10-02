package com.example.customerservice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
    private List<Order> orders;
    private List<SupportTicket> supportTickets;



    public Customer(String name, String email, String password, String address, String phone) {
        this.customerId = UUID.randomUUID().toString();
        this.orders = new ArrayList<>();
        this.supportTickets = new ArrayList<>();
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    public void register(String name, String email, String password, String address, String phone) {
        this.name = name;
        this.email = email;
        this.password = hashPassword(password);
        this.address = address;
        this.phone = phone;
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && verifyPassword(password, this.password);
    }

    public void updateProfile(String name, String email, String address, String phone) {
        if (name != null) this.name = name;
        if (email != null) this.email = email;
        if (address != null) this.address = address;
        if (phone != null) this.phone = phone;
    }

    public List<Order> getOrderHistory() {
        return this.orders;
    }

    public SupportTicket submitSupportTicket(String issue) {
        SupportTicket ticket = new SupportTicket(this.customerId, issue);
        this.supportTickets.add(ticket);
        return ticket;
    }

    public SupportTicket trackSupportTicket(String ticketId) {
        for (SupportTicket ticket : supportTickets) {
            if (ticket.getTicketId().equals(ticketId)) {
                return ticket;
            }
        }
        return null;
    }

    private String hashPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    private boolean verifyPassword(String password, String hashedPassword) {
        return hashedPassword.equals(hashPassword(password));
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public List<Order> getOrders() { return orders; }
    public List<SupportTicket> getSupportTickets() { return supportTickets; }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
