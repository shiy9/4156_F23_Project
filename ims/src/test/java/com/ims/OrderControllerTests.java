package com.ims;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.ims.constants.ItemMessages;
import com.ims.constants.OrderMessages;
import com.ims.controller.OrderController;
import com.ims.entity.Order;
import com.ims.entity.OrderDetail;
import com.ims.entity.OrderJoinOrderDetail;
import com.ims.service.ItemManagementService;
import com.ims.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

  @Mock
  private ItemManagementService itemManagementService;

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
  }

  @Test
  public void createOrderTest() throws Exception {
    String orderJson = "{ \"clientId\": 1, \"itemId\": 101, \"type\": \"buy\", \"orderStatus\": " +
            "\"PENDING\" }";
    doNothing().when(orderService).createOrder(any());

    mockMvc.perform(MockMvcRequestBuilders.post("/order/create")
                    .contentType("application/json")
                    .content(orderJson))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(OrderMessages.ORDER_CREATE_SUCCESS));
  }

  @Test
  public void retrieveOrdersByClientIdTest() throws Exception {
    Order mockOrder = new Order();
    mockOrder.setOrderId(1);
    mockOrder.setClientId(1);
    mockOrder.setType("buy");
    mockOrder.setOrderStatus("PENDING");

    List<Order> mockOrders = Collections.singletonList(mockOrder);
    when(orderService.retrieveOrdersByClientId(1)).thenReturn(mockOrders);

    mockMvc.perform(MockMvcRequestBuilders.get("/order/retrieve/client/1")
                    .contentType("application/json"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderId").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].clientId").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderStatus").value("PENDING"));
  }

  @Test
  public void retrieveOrdersByClientIdTestEmpty() throws Exception {
    List<Order> mockOrders = Collections.emptyList();
    when(orderService.retrieveOrdersByClientId(anyInt())).thenReturn(mockOrders);

    mockMvc.perform(MockMvcRequestBuilders.get("/order/retrieve/client/1")
                    .contentType("application/json"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string(OrderMessages.ORDER_RETRIEVE_FAILURE_CLIENT));
  }

  @Test
  public void retrieveOrdersByOrderIdTest() throws Exception {
    OrderJoinOrderDetail mockOrderJoinOrderDetail = new OrderJoinOrderDetail();
    mockOrderJoinOrderDetail.setOrderId(3);
    mockOrderJoinOrderDetail.setClientId(5);
    mockOrderJoinOrderDetail.setType("table");
    mockOrderJoinOrderDetail.setOrderStatus("PENDING");

    List<OrderJoinOrderDetail> mockOrderJoinOrderDetails = Collections.singletonList(mockOrderJoinOrderDetail);
    when(orderService.retrieveOrdersById(3)).thenReturn(mockOrderJoinOrderDetails);

    mockMvc.perform(MockMvcRequestBuilders.get("/order/retrieve/order/3")
                    .contentType("application/json"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderId").value(3))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].clientId").value(5))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("table"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderStatus").value("PENDING"));
  }


  @Test
  public void updateOrderTest() throws Exception {
    String updatedOrderJson =
            "{ \"id\": 1, \"clientId\": 1, \"orderStatus\": \"SHIPPED\" }";
    doNothing().when(orderService).updateOrder(any());

    mockMvc.perform(MockMvcRequestBuilders.put("/order/update")
                    .contentType("application/json")
                    .content(updatedOrderJson))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(OrderMessages.ORDER_UPDATE_SUCCESS));
  }

  @Test
  public void deleteOrderTest() throws Exception {
    doNothing().when(orderService).deleteOrder(1);

    mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(OrderMessages.ORDER_DELETE_SUCCESS));
  }

  @Test
  public void createOrderDetailTest() throws Exception {
    String orderDetailJson = "{\"orderId\": 1, \"itemId\": 1, \"quantity\": 2, \"amount\": 150.0 }";
    when(itemManagementService.decreaseItem(any())).thenReturn(ItemMessages.UPDATE_SUCCESS);
    doNothing().when(orderService).createOrderDetail(any());

    mockMvc.perform(MockMvcRequestBuilders.post("/order/detail/create")
                    .contentType("application/json")
                    .content(orderDetailJson))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(OrderMessages.ORDER_DETAIL_CREATE_SUCCESS));
  }

  @Test
  public void createOrderDetailTestBad() throws Exception {
    String orderDetailJson = "{\"orderId\": 1, \"itemId\": 1, \"quantity\": 2, \"amount\": 150.0 }";
    when(itemManagementService.decreaseItem(any())).thenReturn(ItemMessages.UPDATE_FAILURE);

    mockMvc.perform(MockMvcRequestBuilders.post("/order/detail/create")
                    .contentType("application/json")
                    .content(orderDetailJson))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string(ItemMessages.UPDATE_FAILURE));
  }

  @Test
  public void updateOrderDetailTest() throws Exception {
    String updatedOrderDetailJson =
            "{ \"orderId\": 1, \"itemId\": 101, \"quantity\": 5, \"amount\": 150.0 }";
    doNothing().when(orderService).updateOrderDetail(any());

    mockMvc.perform(MockMvcRequestBuilders.put("/order/detail/update")
                    .contentType("application/json")
                    .content(updatedOrderDetailJson))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(OrderMessages.ORDER_DETAIL_UPDATE_SUCCESS));
  }

  @Test
  public void retrieveOrderDetailByItemIdTest() throws Exception {
    OrderDetail mockOrderDetail = new OrderDetail();
    mockOrderDetail.setOrderId(1);
    mockOrderDetail.setItemId(101);
    mockOrderDetail.setQuantity(20);
    mockOrderDetail.setAmount(2.0);
    List<OrderDetail> mockOrderDetails = Collections.singletonList(mockOrderDetail);
    when(orderService.retrieveOrderDetailByItemId(101)).thenReturn(mockOrderDetails);

    mockMvc.perform(MockMvcRequestBuilders.get("/order/detail/retrieve/item_id/101"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderId").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].itemId").value(101))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(20))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(2.0));
  }

  @Test
  public void retrieveOrderDetailByItemIdTestBad() throws Exception {
    List<OrderDetail> mockOrderDetails = Collections.emptyList();
    when(orderService.retrieveOrderDetailByItemId(101)).thenReturn(mockOrderDetails);

    mockMvc.perform(MockMvcRequestBuilders.get("/order/detail/retrieve/item_id/101"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void retrieveOrderDetailByOrderIdTest() throws Exception {
    OrderDetail mockOrderDetail = new OrderDetail();
    mockOrderDetail.setOrderId(2);
    mockOrderDetail.setItemId(203);
    mockOrderDetail.setQuantity(200);
    mockOrderDetail.setAmount(2.0);
    List<OrderDetail> mockOrderDetails = Collections.singletonList(mockOrderDetail);
    when(orderService.retrieveOrderDetailByOrderId(2)).thenReturn(mockOrderDetails);

    mockMvc.perform(MockMvcRequestBuilders.get("/order/detail/retrieve/order_id/2"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderId").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].itemId").value(203))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(2.0));
  }

  @Test
  public void retrieveOrderDetailByOrderIdTestBad() throws Exception {
    List<OrderDetail> mockOrderDetails = Collections.emptyList();
    when(orderService.retrieveOrderDetailByOrderId(anyInt())).thenReturn(mockOrderDetails);

    mockMvc.perform(MockMvcRequestBuilders.get("/order/detail/retrieve/order_id/2"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void deleteOrderDetailTest() throws Exception {
    doNothing().when(orderService).deleteOrderDetail(1);

    mockMvc.perform(MockMvcRequestBuilders.delete("/order/detail/delete/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(OrderMessages.ORDER_DETAIL_DELETE_SUCCESS));
  }

  @Test
  public void returnAlertTest() throws Exception {
    Date date = new Date();
    OrderDetail mockOrderDetail = new OrderDetail();
    mockOrderDetail.setOrderId(2);
    mockOrderDetail.setItemId(203);
    mockOrderDetail.setQuantity(200);
    mockOrderDetail.setAmount(2.0);
    mockOrderDetail.setDueDate(date);
    List<OrderDetail> mockOrderDetails = Collections.singletonList(mockOrderDetail);
    when(orderService.getReturnAlertItem()).thenReturn(mockOrderDetails);

    mockMvc.perform(MockMvcRequestBuilders.get("/order/returnAlert"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderId").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].itemId").value(203))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(2.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].dueDate").value(date));
  }

  @Test
  public void returnAlertTestBad() throws Exception {
    List<OrderDetail> mockOrderDetails = Collections.emptyList();
    when(orderService.getReturnAlertItem()).thenReturn(mockOrderDetails);

    mockMvc.perform(MockMvcRequestBuilders.get("/order/returnAlert"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void expirationAlertTest() throws Exception {
    String dateString = "2023-11-27";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = dateFormat.parse(dateString);
    OrderDetail mockOrderDetail = new OrderDetail();
    mockOrderDetail.setOrderId(2);
    mockOrderDetail.setItemId(203);
    mockOrderDetail.setQuantity(200);
    mockOrderDetail.setAmount(2.0);
    mockOrderDetail.setDueDate(date);
    List<OrderDetail> mockOrderDetails = Collections.singletonList(mockOrderDetail);
    when(orderService.getExpirationAlertItem()).thenReturn(mockOrderDetails);

    mockMvc.perform(MockMvcRequestBuilders.get("/order/expirationAlert"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderId").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].itemId").value(203))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value(2.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].dueDate").value(date));
  }

  @Test
  public void expirationAlertTestBad() throws Exception {
    List<OrderDetail> mockOrderDetails = Collections.emptyList();
    when(orderService.getExpirationAlertItem()).thenReturn(mockOrderDetails);

    mockMvc.perform(MockMvcRequestBuilders.get("/order/expirationAlert"))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

}



