package com.ims;

import com.ims.controller.OrderController;
import com.ims.service.OrderService;
import com.ims.entity.Order;
import com.ims.constants.OrderMessages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(value = MockitoExtension.class)
public class OrderControllerTests {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void createOrderTest() throws Exception {
        String orderJSON = "{ \"userId\": 1, \"itemId\": 101, \"orderStatus\": \"PENDING\" }";
        doNothing().when(orderService).createOrder(any());

        mockMvc.perform(post("/order/create")
                        .contentType("application/json")
                        .content(orderJSON))
                .andExpect(status().isOk())
                .andExpect(content().string(OrderMessages.CREATE_SUCCESS));
    }

    @Test
    public void retrieveOrdersByUserIdTest() throws Exception {
        Order mockOrder = new Order();
        mockOrder.setOrderId(1);
        mockOrder.setUserId(1);
        mockOrder.setItemId(101);
        mockOrder.setOrderStatus("PENDING");
        List<Order> mockOrders = Collections.singletonList(mockOrder);
        when(orderService.retrieveOrdersByUserId(1)).thenReturn(mockOrders);

        mockMvc.perform(get("/order/retrieve/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(mockOrders)));
    }

    @Test
    public void updateOrderTest() throws Exception {
        String updatedOrderJSON = "{ \"id\": 1, \"userId\": 1, \"itemId\": 101, \"orderStatus\": \"SHIPPED\" }";
        doNothing().when(orderService).updateOrder(any());

        mockMvc.perform(put("/order/update")
                        .contentType("application/json")
                        .content(updatedOrderJSON))
                .andExpect(status().isOk())
                .andExpect(content().string(OrderMessages.UPDATE_SUCCESS));
    }

    @Test
    public void deleteOrderTest() throws Exception {
        doNothing().when(orderService).deleteOrder(1);

        mockMvc.perform(delete("/order/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(OrderMessages.DELETE_SUCCESS));
    }
}
