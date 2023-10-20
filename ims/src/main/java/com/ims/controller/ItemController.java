package com.ims.controller;

import com.ims.entity.Item;
import com.ims.entity.ItemLocation;
import com.ims.entity.Location;
import com.ims.service.ItemManagementService;
import com.ims.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {
    @Autowired
    private LocationService locationService;

    @Autowired
    private ItemManagementService itemManagementService;

    @GetMapping(value="/location/get/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Integer id) {
        Location location = locationService.getLocationById(id);
        if (location != null) {
            return ResponseEntity.ok(location);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value="/location/create")
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        int result = locationService.insert(location);
        if (result > 0) {
            return ResponseEntity.ok(location);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value="/item/get/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Integer id) {
        Item item = itemManagementService.getItemById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value="/item/create")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        int result = itemManagementService.insertItem(item);
        if (result > 0) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.badRequest().build();
        }
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
    public ResponseEntity<ItemLocation> createItemLocation(@RequestBody ItemLocation itemLocation) {
        int result = itemManagementService.insertItemLocation(itemLocation);
        if (result > 0) {
            return ResponseEntity.ok(itemLocation);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
