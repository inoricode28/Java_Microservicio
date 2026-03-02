package com.example.Java2.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final List<Customer> customers = new ArrayList<>();

    // Constructor para poblar la lista de clientes por defecto
    public CustomerController() {
        // Agregar 3 clientes por defecto
        customers.add(new Customer("1", "Juan", "Pérez", "juan.perez@email.com", "Calle Falsa 123"));
        customers.add(new Customer("2", "Ana", "Gómez", "ana.gomez@email.com", "Calle Real 456"));
        customers.add(new Customer("3", "Carlos", "López", "carlos.lopez@email.com", "Calle 10 789"));
    }

    // Endpoint para obtener todos los clientes
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customers;
    }

    // Endpoint para obtener un cliente por ID
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable String id) {
        Optional<Customer> customer = customers.stream()
                .filter(c -> c.id().equals(id))
                .findFirst();
        return customer.orElse(null);  // Si no lo encuentra, devuelve null
    }

    // Endpoint para agregar un nuevo cliente
    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        customers.add(customer);
        return customer;
    }
}