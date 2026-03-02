package com.example.Java3.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final List<Order> orders = new ArrayList<>();
    private final CustomerFeignClient customerFeignClient;
    private final InventoryFeign inventoryFeign;

    public OrderController(CustomerFeignClient customerFeignClient, InventoryFeign inventoryFeign) {
        this.customerFeignClient = customerFeignClient;
        this.inventoryFeign = inventoryFeign;
    }

    // Endpoint para crear un pedido
    @PostMapping
    public Order createOrder(@RequestBody OrderRequest orderRequest) {
        // Obtener información del cliente desde el microservicio de clientes
        Customer customer = customerFeignClient.getCustomerById(orderRequest.customerId());

        // Verificar que se encontró el cliente
        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        // Obtener información de la computadora desde el microservicio de inventario
        Computer computer = inventoryFeign.getComputers().stream()
                .filter(c -> c.name().equals(orderRequest.computerName()))
                .findFirst()
                .orElse(null);

        // Verificar si la computadora existe
        if (computer == null) {
            throw new RuntimeException("Computer not found");
        }

        // Calcular el precio total
        double totalPrice = orderRequest.quantity() * computer.price();

        // Crear el pedido
        Order order = new Order(
                orderRequest.orderId(),
                computer.name(),
                customer.firstName(),
                customer.lastName(),
                orderRequest.quantity(),
                totalPrice
        );

        // Guardar el pedido en memoria
        orders.add(order);
        return order;
    }

    // Endpoint para obtener todos los pedidos
    @GetMapping
    public List<Order> getAllOrders() {
        return orders;
    }
}