package com.example.customerservice.controller;

import com.example.customerservice.dto.CustomerLoginRequest;
import com.example.customerservice.dto.CustomerRegistrationRequest;
import com.example.customerservice.dto.CustomerUpdateRequest;
import com.example.customerservice.dto.SupportTicketRequest;
import com.example.customerservice.model.Customer;
import com.example.customerservice.model.SupportTicket;
import com.example.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // POST /register: Register a new customer.
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CustomerRegistrationRequest request) {
        customerService.register(request);
        return ResponseEntity.ok("Registration successful");
    }

    // POST /login: Authenticate a customer.
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CustomerLoginRequest request) {
        boolean success = customerService.login(request.getEmail(),
                request.getPassword()).getStatusCode()==HttpStatus.OK;
        if (success) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    // GET /profile/{customer_id}: Retrieve customer profile.
    @GetMapping("/profile/{customerId}")
    public ResponseEntity<Customer> getProfile(@PathVariable String customerId) {
        Customer customer = customerService.getProfile(customerId).getBody();
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /profile/{customer_id}: Update customer profile.
    @PutMapping("/profile/{customerId}")
    public ResponseEntity<String> updateProfile(
            @PathVariable String customerId,
            @RequestBody CustomerUpdateRequest request) {
        boolean updated =
                customerService.updateProfile(customerId, request).getStatusCode()== HttpStatus.OK;
        if (updated) {
            return ResponseEntity.ok("Profile updated");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /support/ticket: Submit a support ticket.
    @PostMapping("/support/ticket")
    public ResponseEntity<SupportTicket> submitSupportTicket(@RequestBody SupportTicketRequest request) {
        SupportTicket ticket = customerService.submitSupportTicket(request).getBody();
        return ResponseEntity.ok(ticket);
    }

    // GET /support/ticket/{ticket_id}: Track support ticket status.
    @GetMapping("/support/ticket/{ticketId}")
    public ResponseEntity<SupportTicket> trackSupportTicket(@PathVariable String ticketId) {
        SupportTicket ticket = customerService.trackSupportTicket(ticketId).getBody();
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
