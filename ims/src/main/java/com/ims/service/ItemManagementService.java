package com.ims.service;

import com.ims.entity.Item;
import com.ims.entity.ItemLocation;
import java.util.List;

/**
 * The ItemManagementService interface defines all public business behaviors for operations on the
 * Item entity model and some related entities such as ItemLocation.
 */
public interface ItemManagementService {
  Item getItemByItemId(Integer itemId);

  String insertItem(Item item);

  String updateItem(Item item);

  String generateBarcode(Item item) throws Exception;

  List<Item> getItemsByUserId(Integer userId);

  ItemLocation getItemLocationById(Integer itemId, Integer locationId);

  String insertItemLocation(ItemLocation itemLocation);

  List<ItemLocation> getItemLocationsByItemId(Integer itemId);

  List<ItemLocation> getItemLocationsByLocationId(Integer locationId);
}
