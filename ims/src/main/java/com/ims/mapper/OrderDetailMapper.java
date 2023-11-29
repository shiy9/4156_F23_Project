package com.ims.mapper;

import com.ims.entity.OrderDetail;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper interface for Order operations.
 */
@Mapper
public interface OrderDetailMapper {
  int insert(OrderDetail orderDetail);

  void update(OrderDetail orderDetail);

  int delete(Integer orderId);

  List<OrderDetail> retrieveOrderDetailByOrderId(Integer orderId);

  List<OrderDetail> retrieveOrderDetailByItemId(Integer itemId);

  List<OrderDetail> getReturnAlertItem();

  List<OrderDetail> getExpirationAlertItem();

}
