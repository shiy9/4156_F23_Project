package com.ims.mapper;

import com.ims.entity.Item;
import com.ims.entity.OrderDetail;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface ItemMapper {
  Item getItemByItemId(@Param("itemId") Integer itemId);

  List<Item> getItemsByClientId(Integer clientId);

  int insert(Item item);

  int update(Item item);

  List<Item> getReorderItem();

  int updateStock(OrderDetail orderDetail);
}