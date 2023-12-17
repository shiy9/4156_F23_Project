package com.ims.service.impl;

import com.ims.entity.Order;
import com.ims.entity.OrderDetail;
import com.ims.entity.OrderJoinOrderDetail;
import com.ims.mapper.OrderDetailMapper;
import com.ims.mapper.OrderMapper;
import com.ims.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of various Order Management Service functions.
 */
@Service
public class OrderManagementServiceImpl implements OrderService {

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private OrderDetailMapper orderDetailMapper;

  // Implementations for Order operations
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
  public List<Order> retrieveOrdersByClientId(Integer clientId) {
    return orderMapper.retrieveOrdersByClientId(clientId);
  }

  @Override
  public List<OrderJoinOrderDetail> retrieveOrdersById(Integer orderId) {
    return orderMapper.retrieveOrdersById(orderId);
  }

  // Implementations for OrderDetail operations
  @Override
  public void createOrderDetail(OrderDetail orderDetail) {
    orderDetailMapper.insert(orderDetail);
  }

  @Override
  public void updateOrderDetail(OrderDetail orderDetail) {
    orderDetailMapper.update(orderDetail);
  }

  @Override
  public void deleteOrderDetail(Integer orderId) {
    orderDetailMapper.delete(orderId);
  }

  @Override
  public List<OrderDetail> retrieveOrderDetailByOrderId(Integer orderId) {
    return orderDetailMapper.retrieveOrderDetailByOrderId(orderId);
  }

  @Override
  public List<OrderDetail> retrieveOrderDetailByItemId(Integer itemId) {
    return orderDetailMapper.retrieveOrderDetailByItemId(itemId);
  }

  @Override
  public List<OrderDetail> getReturnAlertItem() {
    return orderDetailMapper.getReturnAlertItem();
  }

  @Override
  public List<OrderDetail> getExpirationAlertItem() {
    return orderDetailMapper.getReturnAlertItem();
  }

  @Override
  public OrderDetail retrieveOrderDetailById(Integer orderId, Integer itemId, Integer locationId) {return orderDetailMapper.retrieveOrderDetailById(orderId, itemId, locationId);}
}
