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
import org.springframework.web.bind.annotation.*;


@RestController
public class ItemController {
    @Autowired
    private LocationService locationService;

    @Autowired
    private ItemManagementService itemManagementService;

    @GetMapping("/location/get/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Integer id) {
        Location location = locationService.getLocationById(id);
        if (location != null) {
            return ResponseEntity.ok(location);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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

    @GetMapping("/item/get/{id}")
    public ResponseEntity<Item> getItemByItemId(@PathVariable Integer id) {
        Item item = itemManagementService.getItemByItemId(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/item/getByUserId/{userId}")
    public ResponseEntity<List<Item>> getItemsByUserId(@PathVariable Integer userId) {
        List<Item> items = itemManagementService.getItemsByUserId(userId);
        if (items != null) {
            return ResponseEntity.ok(items);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/item/create")
    public ResponseEntity<String> createItem(@RequestBody Item item) {
        String result = itemManagementService.insertItem(item);
        if (result.equals(ItemMessages.INSERT_SUCCESS)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/item/update")
    public ResponseEntity<String> updateItem(@RequestBody Item item) {
        String result = itemManagementService.updateItem(item);
        if (result.equals(ItemMessages.UPDATE_SUCCESS)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

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

    @GetMapping("/itemLocation/getByItemId/{itemId}")
    public ResponseEntity<List<ItemLocation>> getItemLocationsByItemId(@PathVariable Integer itemId) {
        List<ItemLocation> itemLocations = itemManagementService.getItemLocationsByItemId(itemId);
        if (itemLocations != null) {
            return ResponseEntity.ok(itemLocations);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/itemLocation/getByLocationId/{locationId}")
    public ResponseEntity<List<ItemLocation>> getItemLocationsByLocationId(@PathVariable Integer locationId) {
        List<ItemLocation> itemLocations = itemManagementService.getItemLocationsByLocationId(locationId);
        if (itemLocations != null) {
            return ResponseEntity.ok(itemLocations);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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
}
