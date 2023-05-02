package com.quan.ecommercev2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quan.ecommercev2.Model.Address;
import com.quan.ecommercev2.Model.Order;
import com.quan.ecommercev2.Service.OrderService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<String> processCheckout(@RequestParam Integer userId, @RequestParam Long cartId,
            @RequestBody Address address) throws MessagingException {
        orderService.checkout(userId, cartId, address);
        return ResponseEntity.ok("checkout successful");
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getOrdersByUserId(@RequestParam Integer userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(orders);
        }
    }

}
