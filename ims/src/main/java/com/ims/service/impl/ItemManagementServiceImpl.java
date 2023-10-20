package com.ims.service.impl;

import com.ims.entity.Item;
import com.ims.entity.ItemLocation;
import com.ims.entity.Location;
import com.ims.mapper.ItemLocationMapper;
import com.ims.mapper.ItemMapper;
import com.ims.mapper.LocationMapper;
import com.ims.service.ItemManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemManagementServiceImpl implements ItemManagementService {

    @Autowired
    private ItemLocationMapper itemLocationMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public Item getItemById(Integer itemId) {
        return itemMapper.getItemById(itemId);
    }

    @Override
    public int insertItem(Item item) {
        return itemMapper.insert(item);
    }

    @Override
    public int updateItem(Item item) {
        return itemMapper.update(item);
    }


    @Override
    public ItemLocation getItemLocationById(Integer itemId, Integer locationId) {
        return itemLocationMapper.getItemLocationById(itemId, locationId);
    }

    @Override
    @Transactional
    public int insertItemLocation(ItemLocation itemLocation) {
        Integer itemId = itemLocation.getItemId();
        Integer locationId = itemLocation.getLocationId();

        Item item = itemMapper.getItemById(itemId);
        Location location = locationMapper.getLocationById(locationId);
        if (item == null || location == null) {
            return -1;
        }

        int insertResult = itemLocationMapper.insert(itemLocation);
        if (insertResult > 0) {
            item.setCurrentStockLevel(item.getCurrentStockLevel() + itemLocation.getQuantityAtLocation());
            return itemMapper.update(item);
        }

        return -1;
    }

    @Override
    public List<ItemLocation> getItemLocationsByItemId(Integer itemId) {
        return itemLocationMapper.getItemLocationsByItemId(itemId);
    }

    @Override
    public List<ItemLocation> getItemLocationsByLocationId(Integer locationId) {
        return itemLocationMapper.getItemLocationsByLocationId(locationId);
    }

}
