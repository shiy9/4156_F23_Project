package com.ims.mapper;

import com.ims.entity.ItemLocation;
import java.util.List;

import com.ims.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * The ItemLocationMapper interface defines all public methods for operations on the ItemLocation
 * model.
 */
@Mapper
public interface ItemLocationMapper {
  ItemLocation getItemLocationById(@Param("itemId") Integer itemId,
                                   @Param("locationId") Integer locationId);

  int insert(ItemLocation itemLocation);

  int update(ItemLocation itemLocation);

  List<ItemLocation> getItemLocationsByItemId(@Param("itemId") Integer itemId);

  List<ItemLocation> getItemLocationsByLocationId(@Param("locationId") Integer locationId);

  int updateStock(OrderDetail orderDetail);

}