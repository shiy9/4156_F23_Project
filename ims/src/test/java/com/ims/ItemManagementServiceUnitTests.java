package com.ims;

import com.ims.constants.ItemMessages;
import com.ims.entity.Item;
import com.ims.entity.ItemLocation;
import com.ims.entity.Location;
import com.ims.mapper.ItemLocationMapper;
import com.ims.mapper.ItemMapper;
import com.ims.mapper.LocationMapper;
import com.ims.service.impl.ItemManagementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemManagementServiceUnitTests {

  @Mock
  private ItemLocationMapper itemLocationMapper;

  @Mock
  private ItemMapper itemMapper;

  @Mock
  private LocationMapper locationMapper;

  @InjectMocks
  private ItemManagementServiceImpl itemManagementService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGetItemByItemId() {
    Integer itemId = 1;
    Item expectedItem = new Item();
    expectedItem.setItemId(itemId);

    when(itemMapper.getItemByItemId(itemId)).thenReturn(expectedItem);

    Item actualItem = itemManagementService.getItemByItemId(itemId);

    verify(itemMapper).getItemByItemId(itemId);
    assertEquals(expectedItem, actualItem);
  }

  @Test
  public void testInsertItem() {
    Item item = new Item(); // Set properties as needed
    item.setItemId(1);
    item.setClientId(1);

    when(itemMapper.insert(item)).thenReturn(1);

    Integer result = itemManagementService.insertItem(item);

    assertEquals(1, result);
  }

  @Test
  public void testUpdateItem() {
    Item item = new Item(); // Set properties as needed
    item.setItemId(1);
    item.setClientId(1);

    when(itemMapper.update(item)).thenReturn(1);

    String result = itemManagementService.updateItem(item);

    assertEquals(ItemMessages.UPDATE_SUCCESS, result);
  }

  @Test
  public void testGetItemsByClientId() {
    Integer clientId = 1;
    Item item1 = new Item();
    item1.setItemId(1);
    item1.setClientId(clientId);
    Item item2 = new Item();
    item2.setItemId(2);
    item2.setClientId(clientId);

    List<Item> expectedItems = Arrays.asList(item1, item2);
    when(itemMapper.getItemsByClientId(clientId)).thenReturn(expectedItems);

    List<Item> actualItems = itemManagementService.getItemsByClientId(clientId);

    verify(itemMapper).getItemsByClientId(clientId);
    assertEquals(expectedItems, actualItems);
  }

  @Test
  public void testGetItemLocationById() {
    Integer itemId = 1;
    Integer locationId = 1;
    Integer quantity = 10;
    ItemLocation expectedLocation = new ItemLocation(itemId, locationId, quantity);

    when(itemLocationMapper.getItemLocationById(itemId, locationId)).thenReturn(expectedLocation);

    ItemLocation actualLocation = itemManagementService.getItemLocationById(itemId, locationId);

    verify(itemLocationMapper).getItemLocationById(itemId, locationId);
    assertEquals(expectedLocation, actualLocation);
  }

  // Test for successful insert
  @Test
  public void testInsertItemLocation_Success() {
    Integer itemId = 1;
    Integer locationId = 1;
    Integer quantity = 10;

    ItemLocation itemLocation = new ItemLocation(itemId, locationId, quantity); // Set properties as needed
    Item item = new Item(); // Set properties as needed
    item.setItemId(itemId);
    item.setCurrentStockLevel(0);
    Location location = new Location(); // Set properties as needed
    location.setLocationId(locationId);

    when(itemMapper.getItemByItemId(itemLocation.getItemId())).thenReturn(item);
    when(locationMapper.getLocationById(itemLocation.getLocationId())).thenReturn(location);
    when(itemLocationMapper.insert(itemLocation)).thenReturn(1);
    when(itemMapper.update(item)).thenReturn(1);

    String result = itemManagementService.insertItemLocation(itemLocation);

    assertEquals(ItemMessages.INSERT_SUCCESS, result);
    assertEquals(quantity, item.getCurrentStockLevel());
  }

  @Test
  public void testInsertItemLocation_InvalidId() {
    ItemLocation itemLocation = new ItemLocation(); // Set properties as needed
    itemLocation.setItemId(1);
    when(itemMapper.getItemByItemId(itemLocation.getItemId())).thenReturn(null);

    String result = itemManagementService.insertItemLocation(itemLocation);
    assertEquals(ItemMessages.INVALID_ITEM_ID, result);
  }

  @Test
  public void testUpdateItemLocation() {
    Integer itemId = 1;
    Integer locationId = 1;
    Integer quantity = 10;
    Integer newQuantity = 20;
    Integer currentStockLevel = 100;

    ItemLocation itemLocation = new ItemLocation(); // Set properties
    itemLocation.setItemId(itemId);
    itemLocation.setLocationId(locationId);
    itemLocation.setQuantityAtLocation(quantity);
    Item item = new Item(); // Set properties
    item.setItemId(itemId);
    item.setCurrentStockLevel(currentStockLevel);
    Location location = new Location(); // Set properties
    location.setLocationId(locationId);
    ItemLocation updatedItemLocation = new ItemLocation(); // Set properties including quantity
    updatedItemLocation.setItemId(itemId);
    updatedItemLocation.setLocationId(locationId);
    updatedItemLocation.setQuantityAtLocation(newQuantity);

    // Setup Mocks
    when(itemMapper.getItemByItemId(itemLocation.getItemId())).thenReturn(item);
    when(locationMapper.getLocationById(itemLocation.getLocationId())).thenReturn(location);
    when(itemLocationMapper.getItemLocationById(itemLocation.getItemId(), itemLocation.getLocationId())).thenReturn(itemLocation);
    when(itemLocationMapper.update(updatedItemLocation)).thenReturn(1);
    when(itemMapper.update(item)).thenReturn(1);

    // Invoke method and assert
    String result = itemManagementService.updateItemLocation(updatedItemLocation);
    assertEquals(ItemMessages.UPDATE_SUCCESS, result);
    assertEquals(currentStockLevel + (newQuantity - quantity), item.getCurrentStockLevel());
  }


  @Test
  public void testGetItemLocationsByItemId() {
    Integer itemId = 1;
    ItemLocation itemLocation1 = new ItemLocation(); // Set properties as needed
    ItemLocation itemLocation2 = new ItemLocation(); // Set properties as needed
    itemLocation1.setItemId(itemId);
    itemLocation2.setItemId(itemId);
    List<ItemLocation> expectedLocations = Arrays.asList(itemLocation1, itemLocation2);

    when(itemLocationMapper.getItemLocationsByItemId(itemId)).thenReturn(expectedLocations);

    List<ItemLocation> actualLocations = itemManagementService.getItemLocationsByItemId(itemId);

    verify(itemLocationMapper).getItemLocationsByItemId(itemId);
    assertEquals(expectedLocations, actualLocations);
  }

  @Test
  public void testGetItemLocationsByLocationId() {
    Integer locationId = 1;
    ItemLocation itemLocation1 = new ItemLocation(); // Set properties as needed
    ItemLocation itemLocation2 = new ItemLocation(); // Set properties as needed
    itemLocation1.setLocationId(locationId);
    itemLocation2.setLocationId(locationId);
    List<ItemLocation> expectedLocations = Arrays.asList(itemLocation1, itemLocation2);

    when(itemLocationMapper.getItemLocationsByLocationId(locationId)).thenReturn(expectedLocations);

    List<ItemLocation> actualLocations = itemManagementService.getItemLocationsByLocationId(locationId);

    verify(itemLocationMapper).getItemLocationsByLocationId(locationId);
    assertEquals(expectedLocations, actualLocations);
  }


}
