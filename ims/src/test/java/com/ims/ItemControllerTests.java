package com.ims;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.constants.ItemMessages;
import com.ims.entity.Item;
import com.ims.entity.ItemLocation;
import com.ims.entity.Location;
import com.ims.service.ItemManagementService;
import com.ims.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ItemManagementService itemManagementService;

    @Test
    public void testGetLocationById() throws Exception {
        Location location = new Location();
        location.setLocationId(10);
        location.setName("Warehouse");
        location.setAddress1("123 Main St");

        // Assuming the locationService's insert method returns the location's ID or name upon successful insertion
        locationService.insert(location);

        mockMvc.perform(get("/location/get/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.locationId").value(10))
                .andExpect(jsonPath("$.name").value("Warehouse"));
    }

    @Test
    public void testCreateLocation() throws Exception {
        Location location = new Location();
        location.setName("Warehouse");
        location.setAddress1("123 Main St");

        mockMvc.perform(post("/location/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(location)))
                .andExpect(status().isOk())
                .andExpect(content().string(ItemMessages.INSERT_SUCCESS));
    }

    @Test
    public void testGetItemById() throws Exception {
        Item item = new Item();
        item.setItemId(10);
        item.setName("Item");
        item.setDescription("Item Description");
        item.setPrice(10.0f);
        item.setCurrentStockLevel(10);

        // Assuming the itemManagementService's insert method returns the item's ID or name upon successful insertion
        itemManagementService.insertItem(item);

        mockMvc.perform(get("/item/get/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.itemId").value(10))
                .andExpect(jsonPath("$.name").value("Item"));
    }

    @Test
    public void testCreateItem() throws Exception {
        Item item = new Item();
        item.setName("Item");
        item.setDescription("Item Description");
        item.setPrice(10.0f);
        item.setCurrentStockLevel(10);

        mockMvc.perform(post("/item/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(content().string(ItemMessages.INSERT_SUCCESS));
    }

    @Test
    public void testGetItemLocationById() throws Exception {
        Item item = new Item();
        item.setItemId(10);
        item.setName("Item");
        item.setDescription("Item Description");
        item.setPrice(10.0f);
        item.setCurrentStockLevel(10);

        Location location = new Location();
        location.setLocationId(10);
        location.setName("Warehouse");
        location.setAddress1("123 Main St");

        itemManagementService.insertItem(item);
        locationService.insert(location);

        ItemLocation itemLocation = new ItemLocation();
        itemLocation.setItemId(10);
        itemLocation.setLocationId(10);
        itemLocation.setQuantityAtLocation(10);

        // Assuming the itemManagementService's insert method returns the item's ID or name upon successful insertion
        itemManagementService.insertItemLocation(itemLocation);

        mockMvc.perform(get("/itemLocation/get/10/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.itemId").value(10))
                .andExpect(jsonPath("$.locationId").value(10))
                .andExpect(jsonPath("$.quantityAtLocation").value(10));
    }

    @Test
    public void testInsertItemLocation() throws Exception {
        Item item = new Item();
        item.setItemId(10);
        item.setName("Item");
        item.setDescription("Item Description");
        item.setPrice(10.0f);
        item.setCurrentStockLevel(10);

        Location location = new Location();
        location.setLocationId(10);
        location.setName("Warehouse");
        location.setAddress1("123 Main St");

        ItemLocation itemLocation = new ItemLocation();
        itemLocation.setItemId(10);
        itemLocation.setLocationId(10);
        itemLocation.setQuantityAtLocation(10);

        mockMvc.perform(post("/itemLocation/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemLocation)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ItemMessages.INVALID_ITEM_ID));

        itemManagementService.insertItem(item);
        locationService.insert(location);

        mockMvc.perform(post("/itemLocation/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemLocation)))
                .andExpect(status().isOk())
                .andExpect(content().string(ItemMessages.INSERT_SUCCESS));
    }

    @Test
    public void testGetItemLocationsByItemId() throws Exception {
        Item item = new Item();
        item.setItemId(10);
        item.setName("Item");
        item.setDescription("Item Description");
        item.setPrice(10.0f);
        item.setCurrentStockLevel(25);

        Location location1 = new Location();
        location1.setLocationId(10);
        location1.setName("Warehouse");
        location1.setAddress1("123 Main St");

        Location location2 = new Location();
        location2.setLocationId(11);
        location2.setName("Warehouse 2");
        location2.setAddress1("123 Columbia St");

        ItemLocation itemLocation1 = new ItemLocation();
        itemLocation1.setItemId(10);
        itemLocation1.setLocationId(10);
        itemLocation1.setQuantityAtLocation(10);

        ItemLocation itemLocation2 = new ItemLocation();
        itemLocation2.setItemId(10);
        itemLocation2.setLocationId(11);
        itemLocation2.setQuantityAtLocation(15);

        itemManagementService.insertItem(item);
        locationService.insert(location1);
        locationService.insert(location2);
        itemManagementService.insertItemLocation(itemLocation1);
        itemManagementService.insertItemLocation(itemLocation2);

        mockMvc.perform(get("/itemLocation/getByItemId/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].itemId").value(10))
                .andExpect(jsonPath("$[0].locationId").value(10))
                .andExpect(jsonPath("$[0].quantityAtLocation").value(10))
                .andExpect(jsonPath("$[1].itemId").value(10))
                .andExpect(jsonPath("$[1].locationId").value(11))
                .andExpect(jsonPath("$[1].quantityAtLocation").value(15));
    }

    @Test
    public void testGetItemLocationsByLocationId() throws Exception {
        Item item1 = new Item();
        item1.setItemId(10);
        item1.setName("Item");
        item1.setDescription("Item Description");
        item1.setPrice(10.0f);
        item1.setCurrentStockLevel(25);

        Item item2 = new Item();
        item2.setItemId(11);
        item2.setName("Item 2");
        item2.setDescription("Item 2 Description");
        item2.setPrice(10.0f);
        item2.setCurrentStockLevel(25);

        Location location = new Location();
        location.setLocationId(10);
        location.setName("Warehouse");
        location.setAddress1("123 Main St");

        ItemLocation itemLocation1 = new ItemLocation();
        itemLocation1.setItemId(10);
        itemLocation1.setLocationId(10);
        itemLocation1.setQuantityAtLocation(10);

        ItemLocation itemLocation2 = new ItemLocation();
        itemLocation2.setItemId(11);
        itemLocation2.setLocationId(10);
        itemLocation2.setQuantityAtLocation(15);

        itemManagementService.insertItem(item1);
        itemManagementService.insertItem(item2);
        locationService.insert(location);
        itemManagementService.insertItemLocation(itemLocation1);
        itemManagementService.insertItemLocation(itemLocation2);

        mockMvc.perform(get("/itemLocation/getByLocationId/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].itemId").value(10))
                .andExpect(jsonPath("$[0].locationId").value(10))
                .andExpect(jsonPath("$[0].quantityAtLocation").value(10))
                .andExpect(jsonPath("$[1].itemId").value(11))
                .andExpect(jsonPath("$[1].locationId").value(10))
                .andExpect(jsonPath("$[1].quantityAtLocation").value(15));
    }
}
