package com.ims.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
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

import java.io.ByteArrayOutputStream;
import java.util.Base64;
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
    public Item getItemByItemId(Integer itemId) {
        return itemMapper.getItemByItemId(itemId);
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
    public String generateBarcode(Item item) throws Exception {
        if (item.getBarcode() != null) {
            return ItemMessages.BARCODE_ALREADY_EXISTS;
        }

        BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
        int width = 300;
        int height = 150;

        // If we scan the barcode, we will see the item name and description
        String data = item.getName() + ": " + item.getDescription();
        BitMatrix bitMatrix = new MultiFormatWriter().encode(data, barcodeFormat, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        byte[] bytes = pngOutputStream.toByteArray();
        item.setBarcode((Base64.getEncoder().encodeToString(bytes)));

        if (itemMapper.update(item) > 0) {
            return ItemMessages.BARCODE_GENERATION_SUCCESS;
        }
        return ItemMessages.BARCODE_GENERATION_FAILURE;
    }

    @Override
    public List<Item> getItemsByUserId(Integer userId) {
        return itemMapper.getItemsByUserId(userId);
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

        Item item = itemMapper.getItemByItemId(itemId);
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
