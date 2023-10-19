package com.ims.mapper;

import com.ims.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {

    // Create order
    @Insert("INSERT INTO `Order` (userId, type, itemId, itemLocationId, quantity, orderDate, amount, dueDate, returnDate, orderStatus) VALUES (#{userId}, #{type}, #{itemId}, #{itemLocationId}, #{quantity}, #{orderDate}, #{amount}, #{dueDate}, #{returnDate}, #{orderStatus})")
    int insert(Order order);

    // Update order
    @Update("UPDATE `Order` SET type=#{type}, itemId=#{itemId}, itemLocationId=#{itemLocationId}, quantity=#{quantity}, orderDate=#{orderDate}, amount=#{amount}, dueDate=#{dueDate}, returnDate=#{returnDate}, orderStatus=#{orderStatus} WHERE orderId=#{orderId}")
    int update(Order order);

    // Delete order
    @Delete("DELETE FROM `Order` WHERE orderId=#{orderId}")
    int delete(Integer orderId);

    // Retrieve orders by userID
    @Select("SELECT * FROM `Order` WHERE userId=#{userId}")
    List<Order> findOrdersByUserId(Integer userId);

    // Retrieve rental/purchase history for a certain item
    @Select("SELECT * FROM `Order` WHERE itemId=#{itemId}")
    List<Order> findOrdersByItemId(Integer itemId);
}
