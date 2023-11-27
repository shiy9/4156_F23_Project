package com.ims.controller;

import com.ims.constants.ItemMessages;
import com.ims.constants.OrderMessages;
import com.ims.entity.Order;
import com.ims.entity.OrderDetail;
import com.ims.service.ItemManagementService;
import com.ims.service.OrderService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

  @Autowired
  private ItemManagementService itemManagementService;

  //Order related
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @PostMapping("/create")
  public ResponseEntity<String> createOrder(@RequestBody Order order) {
    orderService.createOrder(order);
    return ResponseEntity.ok(OrderMessages.ORDER_CREATE_SUCCESS);
  }

  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @PutMapping("/update")
  public ResponseEntity<String> updateOrder(@RequestBody Order order) {
    orderService.updateOrder(order);
    return ResponseEntity.ok(OrderMessages.ORDER_UPDATE_SUCCESS);
  }

  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @DeleteMapping("/delete/{orderId}")
  public ResponseEntity<String> deleteOrder(@PathVariable Integer orderId) {
    orderService.deleteOrder(orderId);
    return ResponseEntity.ok(OrderMessages.ORDER_DELETE_SUCCESS);
  }

  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
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
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/retrieve/client/{clientId}")
  public ResponseEntity<?> retrieveOrdersByClientId(@PathVariable Integer clientId) {
    List<Order> orders = orderService.retrieveOrdersByClientId(clientId);
    if (!orders.isEmpty()) {
      return ResponseEntity.ok(orders);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(OrderMessages.ORDER_RETRIEVE_FAILURE_CLIENT);
    }
  }

  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/retrieve/order/{orderId}")
  public ResponseEntity<?> retrieveOrdersById(@PathVariable Integer orderId) {
    List<Order> orders = orderService.retrieveOrdersById(orderId);
    if (!orders.isEmpty()) {
      return ResponseEntity.ok(orders);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(OrderMessages.ORDER_RETRIEVE_FAILURE);
    }
  }

  //OrderDetail related
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @Transactional(propagation = Propagation.REQUIRED)
  @PostMapping("/detail/create")
  public ResponseEntity<String> createOrderDetail(@RequestBody OrderDetail orderDetail) {
    String result = itemManagementService.decreaseItem(orderDetail);
    if (result.equals(ItemMessages.UPDATE_SUCCESS)) {
      orderService.createOrderDetail(orderDetail);
      return ResponseEntity.ok(OrderMessages.ORDER_DETAIL_CREATE_SUCCESS);
    }
    else {
      return ResponseEntity.badRequest().body(result);
    }
//    orderService.createOrderDetail(orderDetail);
//    return ResponseEntity.ok(OrderMessages.ORDER_DETAIL_CREATE_SUCCESS);
  }

  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @PutMapping("/detail/update")
  public ResponseEntity<String> updateOrderDetail(@RequestBody OrderDetail orderDetail) {
    orderService.updateOrderDetail(orderDetail);
    return ResponseEntity.ok(OrderMessages.ORDER_DETAIL_UPDATE_SUCCESS);
  }

  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @DeleteMapping("/detail/delete/{orderId}")
  public ResponseEntity<String> deleteOrderDetail(@PathVariable Integer orderId) {
    orderService.deleteOrderDetail(orderId);
    return ResponseEntity.ok(OrderMessages.ORDER_DETAIL_DELETE_SUCCESS);
  }

  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/detail/retrieve/order_id/{orderId}")
  public ResponseEntity<?> retrieveOrderDetailByOrderId(@PathVariable Integer orderId) {
    List<OrderDetail> orderDetails = orderService.retrieveOrderDetailByOrderId(orderId);
    if (!orderDetails.isEmpty()) {
      return ResponseEntity.ok(orderDetails);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/detail/retrieve/item_id/{itemId}")
  public ResponseEntity<?> retrieveOrderDetailByItemId(@PathVariable Integer itemId) {
    List<OrderDetail> orderDetails = orderService.retrieveOrderDetailByItemId(itemId);
    if (!orderDetails.isEmpty()) {
      return ResponseEntity.ok(orderDetails);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/returnAlert")
  public ResponseEntity<?> returnAlert() {
    List<OrderDetail> orderDetails = orderService.getReturnAlertItem();
    if (!orderDetails.isEmpty()) {
      return ResponseEntity.ok(orderDetails);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/expirationAlert")
  public ResponseEntity<?> expirationAlert() {
    List<OrderDetail> orderDetails = orderService.getExpirationAlertItem();
    if (!orderDetails.isEmpty()) {
      return ResponseEntity.ok(orderDetails);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
