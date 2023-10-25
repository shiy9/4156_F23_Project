package com.ims.controller;

import com.ims.constants.OrderMessages;
import com.ims.entity.Order;
import com.ims.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to handle order-related endpoints.
 */
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

  /**
   * Endpoint to retrieve orders by user ID.
   *
   * @param userId The user's ID.
   * @return ResponseEntity containing a list of orders associated with the user ID.
   */
  @GetMapping("/retrieve/user/{userId}")
  public ResponseEntity<List<Order>> retrieveOrdersByUserId(@PathVariable Integer userId) {
    List<Order> orders = orderService.retrieveOrdersByUserId(userId);
    if (!orders.isEmpty()) {
      return ResponseEntity.ok(orders);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  /**
   * Endpoint to retrieve orders by item ID.
   *
   * @param itemId The item's ID.
   * @return ResponseEntity containing a list of orders associated with the item ID.
   */
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
