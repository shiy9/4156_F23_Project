package com.ims.service.impl;

import com.ims.entity.Order;
import com.ims.mapper.OrderMapper;
import com.ims.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderMapper orderMapper;

  @Override
  public void createOrder(Order order) {
    orderMapper.insert(order);
  }

  @Override
  public void updateOrder(Order order) {
    orderMapper.update(order);
  }

  @Override
  public void deleteOrder(Integer orderId) {
    orderMapper.delete(orderId);
  }

  @Override
  public List<Order> retrieveOrdersByUserId(Integer userId) {
    return orderMapper.retrieveOrdersByUserId(userId);
  }

  @Override
  public List<Order> retrieveOrdersByItemId(Integer itemId) {
    return orderMapper.retrieveOrdersByItemId(itemId);
  }
}