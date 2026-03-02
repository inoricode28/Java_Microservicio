package com.example.Java1.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "customer-management", url = "${customer.url}")  // URL del microservicio de clientes
public interface CustomerFeignClient {

    @GetMapping("/api/customers")
    List<Customer> getAllCustomers();

    @GetMapping("/api/customers/{id}")
    Customer getCustomerById(@PathVariable("id") String id);
}
