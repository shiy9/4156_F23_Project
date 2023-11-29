package com.ims.service;

import com.ims.entity.Order;
import com.ims.entity.OrderDetail;
import java.util.List;

/**
 * Service interface defining the operations related to orders and order details.
 */
public interface OrderService {

  // Order operations
  void createOrder(Order order);

  void updateOrder(Order order);

  void deleteOrder(Integer orderId);

  List<Order> retrieveOrdersByClientId(Integer clientId);

  List<Order> retrieveOrdersById(Integer orderId);

  // OrderDetail operations
  void createOrderDetail(OrderDetail orderDetail);

  void updateOrderDetail(OrderDetail orderDetail);

  void deleteOrderDetail(Integer orderId);

  List<OrderDetail> retrieveOrderDetailByOrderId(Integer orderId);

  List<OrderDetail> retrieveOrderDetailByItemId(Integer itemId);

  List<OrderDetail> getReturnAlertItem();

  List<OrderDetail> getExpirationAlertItem();

}
