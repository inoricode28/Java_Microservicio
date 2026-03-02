package com.example.Java3.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-management", url = "${customer.url}")
public interface CustomerFeignClient {

    @GetMapping("/api/customers/{id}")
    Customer getCustomerById(@PathVariable("id") String id);
}
