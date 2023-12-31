package com.ims.mapper;

import com.ims.entity.Order;
import com.ims.entity.OrderJoinOrderDetail;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;


/**
 * Mapper interface for Order operations.
 */
@Mapper
public interface OrderMapper {
  int insert(Order order);

  void update(Order order);

  int delete(Integer orderId);

  List<Order> retrieveOrdersByClientId(Integer clientId);

  List<OrderJoinOrderDetail> retrieveOrdersById(Integer orderId);
}
