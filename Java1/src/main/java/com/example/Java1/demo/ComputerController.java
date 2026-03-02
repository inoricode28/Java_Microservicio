package com.example.Java1.demo;

import com.example.Java1.demo.Computer;
import com.example.Java1.demo.Sale;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ComputerController {

    private final List<Computer> computers = new ArrayList<>();  // Memoria local para las computadoras
    private final List<Sale> sales = new ArrayList<>();  // Memoria local para las ventas
    private final CustomerFeignClient customerFeignClient;  // Inyectar el FeignClient de clientes

    // Constructor
    public ComputerController(CustomerFeignClient customerFeignClient) {
        this.customerFeignClient = customerFeignClient;

        // Poblamos algunos productos de ejemplo en la memoria local
        computers.add(new Computer("Laptop HP", "HP", 500.0));
        computers.add(new Computer("MacBook Pro", "Apple", 1500.0));
        computers.add(new Computer("Lenovo ThinkPad", "Lenovo", 1200.0));
    }

    // Endpoint para obtener todas las computadoras
    @GetMapping("/computers")
    public List<Computer> getComputers() {
        return computers;
    }

    // Endpoint para realizar una venta
    @PostMapping("/sale")
    public Sale createSale(@RequestBody Sale sale) {
        // Obtener información del cliente desde el microservicio de clientes
        Customer customer = customerFeignClient.getCustomerById(sale.customerId());

        // Verificar si la computadora seleccionada está disponible
        double totalPrice = sale.quantity() * computers.stream()
                .filter(computer -> computer.name().equals(sale.computerName()))
                .findFirst()
                .map(Computer::price)
                .orElse(0.0);

        if (totalPrice == 0.0) {
            return null;  // No se encontró la computadora o el precio es 0
        }

        // Crear la venta con la información del cliente y la computadora seleccionada
        Sale newSale = new Sale(
                sale.computerName(),
                customer.firstName(),
                customer.lastName(),
                sale.quantity(),
                totalPrice,
                sale.customerId()  // Asegúrate de pasar el customerId
        );
        sales.add(newSale);  // Guardar la venta en memoria

        return newSale;
    }

    // Endpoint para obtener todas las ventas realizadas
    @GetMapping("/sales")
    public List<Sale> getSales() {
        return sales;
    }
}