package com.ims.service.impl;

import com.ims.constants.ItemMessages;
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
    public String insertItem(Item item) {
        if (itemMapper.insert(item) > 0) {
            return ItemMessages.INSERT_SUCCESS;
        }
        return ItemMessages.INSERT_FAILURE;
    }

    @Override
    public String updateItem(Item item) {
        if (itemMapper.update(item) > 0) {
            return ItemMessages.UPDATE_SUCCESS;
        }
        return ItemMessages.UPDATE_FAILURE;
    }

    @Override
    public ItemLocation getItemLocationById(Integer itemId, Integer locationId) {
        return itemLocationMapper.getItemLocationById(itemId, locationId);
    }

    @Override
    @Transactional
    public String insertItemLocation(ItemLocation itemLocation) {
        Integer itemId = itemLocation.getItemId();
        Integer locationId = itemLocation.getLocationId();

        Item item = itemMapper.getItemById(itemId);
        if (item == null) {
            return ItemMessages.INVALID_ITEM_ID;
        }
        Location location = locationMapper.getLocationById(locationId);
        if (location == null) {
            return ItemMessages.INVALID_LOCATION_ID;
        }

        int insertResult = itemLocationMapper.insert(itemLocation);
        if (insertResult > 0) {
            item.setCurrentStockLevel(item.getCurrentStockLevel() + itemLocation.getQuantityAtLocation());
            if (itemMapper.update(item) > 0) {
                return ItemMessages.INSERT_SUCCESS;
            }
        }

        return ItemMessages.INSERT_FAILURE;
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
