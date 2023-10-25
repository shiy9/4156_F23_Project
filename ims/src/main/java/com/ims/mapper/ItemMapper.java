package com.ims.mapper;

import com.ims.entity.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    Item getItemByItemId(Integer itemId);

    List<Item> getItemsByUserId(Integer userId);

    int insert(Item item);

    int update(Item item);
}