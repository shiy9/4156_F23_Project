package com.ims;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.constants.OrderMessages;
import com.ims.controller.OrderController;
import com.ims.entity.Order;
import com.ims.service.OrderService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Tests for the OrderController class.
 * <p>
 * This class contains unit tests that focus on the behavior of the OrderController.
 * </p>
 */
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
    String orderJson = "{ \"userId\": 1, \"itemId\": 101, \"orderStatus\": \"PENDING\" }";
    doNothing().when(orderService).createOrder(any());

    mockMvc.perform(MockMvcRequestBuilders.post("/order/create")
            .contentType("application/json")
            .content(orderJson))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(OrderMessages.CREATE_SUCCESS));
  }

  @Test
  public void retrieveOrdersByUserIdTest() throws Exception {
    Order mockOrder = new Order();
    mockOrder.setOrderId(1);
    mockOrder.setUserId(1);
    mockOrder.setOrderStatus("PENDING");
    List<Order> mockOrders = Collections.singletonList(mockOrder);
    when(orderService.retrieveOrdersByUserId(1)).thenReturn(mockOrders);

    mockMvc.perform(MockMvcRequestBuilders.get("/order/retrieve/user/1")
            .contentType("application/json"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderId").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderStatus").value("PENDING"));
  }

  @Test
  public void updateOrderTest() throws Exception {
    String updatedOrderJson =
            "{ \"id\": 1, \"userId\": 1, \"orderStatus\": \"SHIPPED\" }";
    doNothing().when(orderService).updateOrder(any());

    mockMvc.perform(MockMvcRequestBuilders.put("/order/update")
            .contentType("application/json")
            .content(updatedOrderJson))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(OrderMessages.UPDATE_SUCCESS));
  }

  @Test
  public void deleteOrderTest() throws Exception {
    doNothing().when(orderService).deleteOrder(1);

    mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(OrderMessages.DELETE_SUCCESS));
  }
}
