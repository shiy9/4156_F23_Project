package com.ims.controller;

import com.ims.constants.ItemMessages;
import com.ims.entity.Item;
import com.ims.entity.ItemLocation;
import com.ims.entity.Location;
import com.ims.service.ItemManagementService;
import com.ims.service.LocationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implements Item-related REST API.
 */
@RestController
public class ItemController {
  @Autowired
  private LocationService locationService;

  @Autowired
  private ItemManagementService itemManagementService;

  /**
   * Endpoint to retrieve location by ID.
   *
   * @param id The location ID.
   * @return ResponseEntity containing the location object.
   */
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/location/get/{id}")
  public ResponseEntity<Location> getLocationById(@PathVariable Integer id) {
    Location location = locationService.getLocationById(id);
    if (location != null) {
      return ResponseEntity.ok(location);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Endpoint create a location.
   *
   * @param location The location object.
   * @return The response entity containing the result of the operation.
   */
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @PostMapping("/location/create")
  public ResponseEntity<String> createLocation(@RequestBody Location location) {
    String result = locationService.insert(location);
    if (result.equals(ItemMessages.INSERT_SUCCESS)) {
      return ResponseEntity.ok(result);
    } else {
      // return the response entity with the error message in result
      return ResponseEntity.badRequest().body(result);
    }
  }

  /**
   * Endpoint to retrieve item by ID.
   *
   * @param id The item ID.
   * @return ResponseEntity containing the item object.
   */
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/item/get/{id}")
  public ResponseEntity<Item> getItemByItemId(@PathVariable Integer id) {
    Item item = itemManagementService.getItemByItemId(id);
    if (item != null) {
      return ResponseEntity.ok(item);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Endpoint to retrieve a list of items by user ID.
   *
   * @param clientId The client ID.
   * @return ResponseEntity containing a list of items associated with the user ID.
   */
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/item/getByClientId/{clientId}")
  public ResponseEntity<List<Item>> getItemsByClientId(@PathVariable Integer clientId) {
    List<Item> items = itemManagementService.getItemsByClientId(clientId);
    if (items != null) {
      return ResponseEntity.ok(items);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Endpoint to create an item.
   *
   * @param item The item object.
   * @return ResponseEntity containing the result of the operation.
   */
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @PostMapping("/item/create")
  public ResponseEntity<String> createItem(@RequestBody Item item) {
    String result = itemManagementService.insertItem(item);
    if (result.equals(ItemMessages.INSERT_SUCCESS)) {
      return ResponseEntity.ok(result);
    } else {
      return ResponseEntity.badRequest().body(result);
    }
  }

  /**
   * Endpoint to update an item.
   *
   * @param item The item object.
   * @return ResponseEntity containing the result of the operation.
   */
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @PostMapping("/item/update")
  public ResponseEntity<String> updateItem(@RequestBody Item item) {
    String result = itemManagementService.updateItem(item);
    if (result.equals(ItemMessages.UPDATE_SUCCESS)) {
      return ResponseEntity.ok(result);
    } else {
      return ResponseEntity.badRequest().body(result);
    }
  }

  /**
   * Endpoint to generate a barcode for an item.
   *
   * @param id The item ID.
   * @return ResponseEntity containing the result of the operation.
   */
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/item/generateBarcode/{id}")
  public ResponseEntity<String> generateBarcode(@PathVariable Integer id) {
    Item item = itemManagementService.getItemByItemId(id);
    if (item == null) {
      return ResponseEntity.badRequest().body(ItemMessages.INVALID_ITEM_ID);
    }

    try {
      String result = itemManagementService.generateBarcode(item);
      if (result.equals(ItemMessages.BARCODE_GENERATION_SUCCESS)) {
        return ResponseEntity.ok(result);
      } else {
        return ResponseEntity.badRequest().body(result);
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  /**
   * Endpoint to retrieve the barcode image for an item.
   *
   * @param id The item ID.
   * @return ResponseEntity containing the barcode image in png format.
   */
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/item/barcode/{id}")
  public ResponseEntity<byte[]> getBarcodeImage(@PathVariable("id") Integer id) {
    Item item = itemManagementService.getItemByItemId(id);
    if (item == null) {
      return ResponseEntity.notFound().build();
    }

    String barcode = item.getBarcode();
    if (barcode == null) {
      return ResponseEntity.notFound().build();
    }

    byte[] barcodeImage = java.util.Base64.getDecoder().decode(barcode);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_PNG);

    return new ResponseEntity<>(barcodeImage, headers, HttpStatus.OK);
  }

  /**
   * Endpoint to retrieve item location by item ID and location ID.
   *
   * @param itemId     The item ID.
   * @param locationId The location ID.
   * @return ResponseEntity containing the item location object.
   */
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/itemLocation/get/{itemId}/{locationId}")
  public ResponseEntity<ItemLocation> getItemLocationById(@PathVariable Integer itemId,
                                                          @PathVariable Integer locationId) {
    ItemLocation itemLocation = itemManagementService.getItemLocationById(itemId, locationId);
    if (itemLocation != null) {
      return ResponseEntity.ok(itemLocation);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Endpoint to retrieve item locations by item ID.
   *
   * @param itemId The item ID.
   * @return ResponseEntity containing a list of item location objects.
   */
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/itemLocation/getByItemId/{itemId}")
  public ResponseEntity<List<ItemLocation>> getItemLocationsByItemId(@PathVariable Integer itemId) {
    List<ItemLocation> itemLocations = itemManagementService.getItemLocationsByItemId(itemId);
    if (itemLocations != null) {
      return ResponseEntity.ok(itemLocations);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Endpoint to retrieve item locations by location ID.
   *
   * @param locationId The location ID.
   * @return ResponseEntity containing a list of item location objects.
   */
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/itemLocation/getByLocationId/{locationId}")
  public ResponseEntity<List<ItemLocation>> getItemLocationsByLocationId(@PathVariable
                                                                         Integer locationId) {
    List<ItemLocation> itemLocations =
            itemManagementService.getItemLocationsByLocationId(locationId);
    if (itemLocations != null) {
      return ResponseEntity.ok(itemLocations);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Endpoint to retrieve item locations within 50 miles of a location.
   *
   * @param itemId     The item ID.
   * @param locationId The location ID.
   * @return ResponseEntity containing a list of item location objects.
   */
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/itemLocation/getWithin50Miles/{itemId}/{locationId}")
  public ResponseEntity<List<ItemLocation>> getItemLocationsWithin50Miles(@PathVariable
                                                                          Integer itemId,
                                                                          @PathVariable
                                                                          Integer locationId) {
    List<ItemLocation> itemLocations =
            itemManagementService.getItemLocationsWithin50Miles(itemId, locationId);
    if (itemLocations != null) {
      return ResponseEntity.ok(itemLocations);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Endpoint to create an item location.
   *
   * @param itemLocation The item location object.
   * @return ResponseEntity containing the result of the operation.
   */
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @PostMapping("/itemLocation/create")
  public ResponseEntity<String> createItemLocation(@RequestBody ItemLocation itemLocation) {
    String result = itemManagementService.insertItemLocation(itemLocation);
    if (result.equals(ItemMessages.INSERT_SUCCESS)) {
      return ResponseEntity.ok(result);
    } else {
      // return the response entity with the error message in result
      return ResponseEntity.badRequest().body(result);
    }
  }

  /**
   * Endpoint to show reorder alert.
   * @return ResponseEntity containing the result of the operation.
   */
  @PreAuthorize("hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_WAREHOUSE) or "
          + "hasAuthority(T(com.ims.constants.ClientConstants).CLIENT_TYPE_RETAIL)")
  @GetMapping("/item/reorderAlert")
  public ResponseEntity<List<Item>> reorderAlert() {
    List<Item> items = itemManagementService.getReorderItem();
    if (!items.isEmpty()) {
      return ResponseEntity.ok(items);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}
