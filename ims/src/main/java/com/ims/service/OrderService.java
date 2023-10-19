package com.ims.service;

import com.ims.entity.Order;
import java.util.List;


public interface OrderService {

    void createOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(Integer orderId);

    List<Order> retrieveOrdersByUserId(Integer userId);

    List<Order> retrieveOrdersByItemId(Integer itemId);
}
