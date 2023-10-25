package com.ims.mapper;

import com.ims.entity.Item;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ItemMapper {
  Item getItemByItemId(Integer itemId);

  List<Item> getItemsByUserId(Integer userId);

  int insert(Item item);

  int update(Item item);
}