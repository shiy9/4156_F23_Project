package com.ims.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.ims.constants.ItemMessages;
import com.ims.entity.Item;
import com.ims.entity.ItemLocation;
import com.ims.entity.Location;
import com.ims.entity.OrderDetail;
import com.ims.mapper.ItemLocationMapper;
import com.ims.mapper.ItemMapper;
import com.ims.mapper.LocationMapper;
import com.ims.service.ItemManagementService;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of various Item Management Service functions.
 */
@Service
public class ItemManagementServiceImpl implements ItemManagementService {

  private static final double EARTH_RADIUS = 3958.8; // Radius in miles

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
  public List<Item> getItemsByClientId(Integer clientId) {
    return itemMapper.getItemsByClientId(clientId);
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
  @Transactional
  public String updateItemLocation(ItemLocation itemLocation) {
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

    int oldLocationQuantity = itemLocationMapper.getItemLocationById(itemId, locationId)
            .getQuantityAtLocation();
    int updatedLocationQuantity = itemLocation.getQuantityAtLocation();
    int quantityDifference = updatedLocationQuantity - oldLocationQuantity;

    int updateResult = itemLocationMapper.update(itemLocation);
    if (updateResult > 0) {
      item.setCurrentStockLevel(item.getCurrentStockLevel() + quantityDifference);
      if (itemMapper.update(item) > 0) {
        return ItemMessages.UPDATE_SUCCESS;
      }
    }

    return ItemMessages.UPDATE_FAILURE;
  }

  @Override
  public List<ItemLocation> getItemLocationsByItemId(Integer itemId) {
    return itemLocationMapper.getItemLocationsByItemId(itemId);
  }

  @Override
  public List<ItemLocation> getItemLocationsByLocationId(Integer locationId) {
    return itemLocationMapper.getItemLocationsByLocationId(locationId);
  }

  @Override
  public List<ItemLocation> getItemLocationsWithin50Miles(Integer itemId, Integer locationId) {
    List<ItemLocation> allItemLocations = itemLocationMapper.getItemLocationsByItemId(itemId);
    Location givenLocation = locationMapper.getLocationById(locationId);

    for (ItemLocation itemLocation : allItemLocations) {
      if (Objects.equals(itemLocation.getLocationId(), locationId)
              || itemLocation.getQuantityAtLocation() == 0) {
        allItemLocations.remove(itemLocation);
        continue;
      }

      Location location = locationMapper.getLocationById(itemLocation.getLocationId());
      Double distance = haversineDistance(givenLocation, location);
      if (distance > 50) {
        allItemLocations.remove(itemLocation);
      }
    }

    return allItemLocations;
  }

  /**
   * Calculates the distance between two locations using the Haversine formula.
   *
   * @param location1 The first location.
   * @param location2 The second location.
   * @return The distance between the two locations in miles.
   */
  public static double haversineDistance(Location location1, Location location2) {
    // Convert latitudes and longitudes from degrees to radians
    double latitude1 = Math.toRadians(location1.getLatitude());
    double longitude1 = Math.toRadians(location1.getLongitude());
    double latitude2 = Math.toRadians(location2.getLatitude());
    double longitude2 = Math.toRadians(location2.getLongitude());

    // Haversine formula
    double deltaLatitude = latitude2 - latitude1;
    double deltaLongitude = longitude2 - longitude1;
    double a = Math.pow(Math.sin(deltaLatitude / 2), 2)
            + Math.cos(latitude1) * Math.cos(latitude2)
            * Math.pow(Math.sin(deltaLongitude / 2), 2);
    double c = 2 * Math.asin(Math.sqrt(a));

    return EARTH_RADIUS * c;
  }

  @Override
  public List<Item> getReorderItem() {
    return itemMapper.getReorderItem();
  }

  @Override
  public String decreaseItem(OrderDetail orderDetail) {
    Item item = itemMapper.getItemByItemId(orderDetail.getItemId());
    if (item == null) {
      return ItemMessages.INVALID_ITEM_ID;
    }
    if (item.getCurrentStockLevel() >= orderDetail.getQuantity()) {
      itemMapper.updateStock(orderDetail);
      return ItemMessages.UPDATE_SUCCESS;
    } else {
      return ItemMessages.OUT_OF_CURRENT_STOCK_LEVEL;
    }
  }

}
