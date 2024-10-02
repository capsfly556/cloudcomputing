package com.example.customerservice.service;

import com.example.customerservice.dto.CustomerRegistrationRequest;
import com.example.customerservice.dto.CustomerUpdateRequest;
import com.example.customerservice.dto.SupportTicketRequest;
import com.example.customerservice.model.Customer;
import com.example.customerservice.model.SupportTicket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RestController
public class CustomerService {

    private Map<String, Customer> customerRepository = new HashMap<>();
    private Map<String, SupportTicket> ticketRepository = new HashMap<>();

    @GetMapping("/getNameByEmailAndPassword")
    public ResponseEntity<String> getNameByEmailAndPassword(@RequestParam("Email")String email,@RequestParam("password") String password){
        for(Map.Entry<String, Customer> entry:customerRepository.entrySet()){
            Customer customer=entry.getValue();
            if(Objects.equals(customer.getEmail(), email) && Objects.equals(customer.getPassword(), password)){
                return ResponseEntity.ok(customer.getName());
            }
        }
        return new ResponseEntity<>("Please check your email and password",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/showAllEmailAndPassword")
    public ResponseEntity<StringBuffer> showAllEmailAndPassword(){
        StringBuffer responseBody=new StringBuffer();
        for(Map.Entry<String,Customer> entry:customerRepository.entrySet()){
            Customer customer=entry.getValue();
            responseBody.append(customer.getEmail()+" "+customer.getPassword()+"\n");
        }
        return new ResponseEntity<StringBuffer>(responseBody,HttpStatus.OK);
    }

    @GetMapping({"/hello", "/"})
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Hello, World!");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CustomerRegistrationRequest request) {
        Customer customer = new Customer(request.getName(), request.getEmail(), request.getPassword(),
                request.getAddress(), request.getPhone());
        customerRepository.put(customer.getCustomerId(), customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestParam String email, @RequestParam String password) {
        for (Customer customer : customerRepository.values()) {
            if (customer.getEmail().equals(email) && customer.login(email, password)) {
                return ResponseEntity.ok(true);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }

    @GetMapping("/profile/{customerId}")
    public ResponseEntity<Customer> getProfile(@PathVariable String customerId) {
        Customer customer = customerRepository.get(customerId);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/profile/{customerId}")
    public ResponseEntity<String> updateProfile(@PathVariable String customerId, @RequestBody CustomerUpdateRequest request) {
        Customer customer = customerRepository.get(customerId);
        if (customer != null) {
            customer.updateProfile(request.getName(), request.getEmail(),
                    request.getAddress(), request.getPhone());
            return ResponseEntity.ok("Profile updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
    }

    @PostMapping("/support-ticket")
    public ResponseEntity<SupportTicket> submitSupportTicket(@RequestBody SupportTicketRequest request) {
        Customer customer = customerRepository.get(request.getCustomerId());
        if (customer != null) {
            SupportTicket ticket = customer.submitSupportTicket(request.getIssue());
            ticketRepository.put(ticket.getTicketId(), ticket);
            return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/support-ticket/{ticketId}")
    public ResponseEntity<SupportTicket> trackSupportTicket(@PathVariable String ticketId) {
        SupportTicket ticket = ticketRepository.get(ticketId);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
