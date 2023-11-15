package com.ims.mapper;

import com.ims.entity.Item;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ItemMapper {
  Item getItemByItemId(Integer itemId);

  List<Item> getItemsByClientId(Integer clientId);

  int insert(Item item);

  int update(Item item);
}