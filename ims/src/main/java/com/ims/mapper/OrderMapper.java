package com.ims.mapper;

import com.ims.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OrderMapper {
    int insert(Order order);

    void update(Order order);

    int delete(Integer orderId);

    List<Order> retrieveOrdersByUserId(Integer userId);

    List<Order> retrieveOrdersByItemId(Integer itemId);
}
