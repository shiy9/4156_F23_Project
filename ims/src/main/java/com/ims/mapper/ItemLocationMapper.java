package com.ims.mapper;

import com.ims.entity.ItemLocation;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface ItemLocationMapper {
  ItemLocation getItemLocationById(@Param("itemId") Integer itemId, @Param("locationId") Integer locationId);

  int insert(ItemLocation itemLocation);

  List<ItemLocation> getItemLocationsByItemId(@Param("itemId") Integer itemId);

  List<ItemLocation> getItemLocationsByLocationId(@Param("locationId") Integer locationId);

}