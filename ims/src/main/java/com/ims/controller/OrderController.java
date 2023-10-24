package com.ims.controller;

import com.ims.entity.Order;
import com.ims.service.OrderService;
import com.ims.constants.OrderMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Collections;


import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        orderService.createOrder(order);
        return ResponseEntity.ok(OrderMessages.CREATE_SUCCESS);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateOrder(@RequestBody Order order) {
        orderService.updateOrder(order);
        return ResponseEntity.ok(OrderMessages.UPDATE_SUCCESS);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok(OrderMessages.DELETE_SUCCESS);
    }

    @GetMapping("/retrieve/user/{userId}")
    public ResponseEntity<List<Order>> retrieveOrdersByUserId(@PathVariable Integer userId) {
        List<Order> orders = orderService.retrieveOrdersByUserId(userId);
        if (!orders.isEmpty()) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/retrieve/item/{itemId}")
    public ResponseEntity<List<Order>> retrieveOrdersByItemId(@PathVariable Integer itemId) {
        List<Order> orders = orderService.retrieveOrdersByItemId(itemId);
        if (!orders.isEmpty()) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

