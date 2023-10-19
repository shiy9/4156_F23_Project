package com.ims.controller;

import com.ims.entity.Order;
import com.ims.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public String createOrder(@RequestBody Order order) {
        orderService.createOrder(order);
        return "Order Created Successfully";
    }

    @PutMapping("/update")
    public String updateOrder(@RequestBody Order order) {
        orderService.updateOrder(order);
        return "Order Updated Successfully";
    }

    @DeleteMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
        return "Order Deleted Successfully";
    }

    @GetMapping("/retrieve/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Integer userId) {
        return orderService.retrieveOrdersByUserId(userId);
    }

    @GetMapping("/retrieve/item/{itemId}")
    public List<Order> getOrdersByItemId(@PathVariable Integer itemId) {
        return orderService.retrieveOrdersByItemId(itemId);
    }
}

