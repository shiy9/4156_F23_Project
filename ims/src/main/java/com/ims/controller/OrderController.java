package com.ims.controller;

import com.ims.constants.OrderMessages;
import com.ims.entity.Order;
import com.ims.entity.OrderDetail;
import com.ims.service.OrderService;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
  private OrderService OrderService;

  //Order related
  @PostMapping("/create")
  public ResponseEntity<String> createOrder(@RequestBody Order order) {
    OrderService.createOrder(order);
    return ResponseEntity.ok(OrderMessages.ORDER_CREATE_SUCCESS);
  }

  @PutMapping("/update")
  public ResponseEntity<String> updateOrder(@RequestBody Order order) {
    OrderService.updateOrder(order);
    return ResponseEntity.ok(OrderMessages.ORDER_UPDATE_SUCCESS);
  }

  @DeleteMapping("/delete/{orderId}")
  public ResponseEntity<String> deleteOrder(@PathVariable Integer orderId) {
    OrderService.deleteOrder(orderId);
    return ResponseEntity.ok(OrderMessages.ORDER_DELETE_SUCCESS);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(OrderMessages.ORDER_INVALID_ID);
  }

  /**
   * Endpoint to retrieve orders by client ID.
   *
   * @param clientId The client's ID.
   * @return ResponseEntity containing a list of orders associated with the client ID.
   */
  @GetMapping("/retrieve/client/{clientId}")
  public ResponseEntity<String> retrieveOrdersByClientId(@PathVariable Integer clientId) {
    List<Order> orders = OrderService.retrieveOrdersByClientId(clientId);
    if (!orders.isEmpty()) {
      return ResponseEntity.ok(orders.toString());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(OrderMessages.ORDER_RETRIEVE_FAILURE_CLIENT);
    }
  }

  @GetMapping("/retrieve/order/{orderId}")
  public ResponseEntity<String> retrieveOrdersById(@PathVariable Integer orderId) {
    List<Order> orders = OrderService.retrieveOrdersById(orderId);
    if (!orders.isEmpty()) {
      return ResponseEntity.ok(orders.toString());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(OrderMessages.ORDER_RETRIEVE_FAILURE);
    }
  }

  //OrderDetail related
  @PostMapping("/detail/create")
  public ResponseEntity<String> createOrderDetail(@RequestBody OrderDetail orderDetail) {
    OrderService.createOrderDetail(orderDetail);
    return ResponseEntity.ok(OrderMessages.ORDER_DETAIL_CREATE_SUCCESS);
  }

  @PutMapping("/detail/update")
  public ResponseEntity<String> updateOrderDetail(@RequestBody OrderDetail orderDetail) {
    OrderService.updateOrderDetail(orderDetail);
    return ResponseEntity.ok(OrderMessages.ORDER_DETAIL_UPDATE_SUCCESS);
  }

  @DeleteMapping("/detail/delete/{orderId}")
  public ResponseEntity<String> deleteOrderDetail(@PathVariable Integer orderId) {
    OrderService.deleteOrderDetail(orderId);
    return ResponseEntity.ok(OrderMessages.ORDER_DETAIL_DELETE_SUCCESS);
  }

  @GetMapping("/detail/retrieve/order_id/{orderId}")
  public ResponseEntity<List<OrderDetail>> retrieveOrderDetailByOrderId(@PathVariable Integer orderId) {
    List<OrderDetail> orderDetails = OrderService.retrieveOrderDetailByOrderId(orderId);
    if (!orderDetails.isEmpty()) {
      return ResponseEntity.ok(orderDetails);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @GetMapping("/detail/retrieve/item_id/{itemId}")
  public ResponseEntity<List<OrderDetail>> retrieveOrderDetailByItemId(@PathVariable Integer itemId) {
    List<OrderDetail> orderDetails = OrderService.retrieveOrderDetailByItemId(itemId);
    if (!orderDetails.isEmpty()) {
      return ResponseEntity.ok(orderDetails);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
