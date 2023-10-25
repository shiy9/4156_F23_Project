package com.ims.service;

import com.ims.entity.Order;
import java.util.List;

/**
 * Service interface defining the operations related to orders.
 */
public interface OrderService {

  void createOrder(Order order);

  void updateOrder(Order order);

  void deleteOrder(Integer orderId);

  List<Order> retrieveOrdersByUserId(Integer userId);

  List<Order> retrieveOrdersByItemId(Integer itemId);
}
