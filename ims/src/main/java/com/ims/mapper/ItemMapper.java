package com.ims.mapper;

import com.ims.entity.Item;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {
    Item getItemById(Integer itemId);

    int insert(Item item);

    int update(Item item);
}