package com.ims;

import com.ims.entity.Order;
import com.ims.mapper.OrderMapper;
import com.ims.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceTests {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapperMock;

    @BeforeEach
    void setUp() {
        Order order1 = new Order();
        order1.setOrderId(1);
        order1.setUserId(1);
        order1.setType("buy");
        order1.setItemId(101);
        order1.setItemLocationId(1);
        order1.setQuantity(2);
        order1.setOrderDate(java.sql.Date.valueOf("2023-10-10"));
        order1.setAmount(100.0);
        order1.setOrderStatus("In progress");

        Order order2 = new Order();
        order2.setOrderId(2);
        order2.setUserId(1);
        order2.setType("rent");
        order2.setItemId(102);
        order2.setItemLocationId(2);
        order2.setQuantity(1);
        order2.setOrderDate(java.sql.Date.valueOf("2023-10-10"));
        order2.setAmount(50.0);
        order2.setDueDate(java.sql.Date.valueOf("2023-10-20"));
        order2.setOrderStatus("Overdue");

        List<Order> orders = Arrays.asList(order1, order2);

        //when(orderMapperMock.findOrdersByUserId(1)).thenReturn(orders);
        //when(orderMapperMock.findOrdersByItemId(101)).thenReturn(Arrays.asList(order1));
        //when(orderMapperMock.findOrdersByItemId(102)).thenReturn(Arrays.asList(order2));
    }

    @Test
    void testRetrieveOrdersByUserId() {
        List<Order> orders = orderService.retrieveOrdersByUserId(1);
        assertEquals(2, orders.size());
        assertEquals("buy", orders.get(0).getType());
        assertEquals("rent", orders.get(1).getType());
    }

    @Test
    void testCreateOrder() {
        Order newOrder = new Order();
        newOrder.setUserId(2);
        newOrder.setType("buy");

        orderService.createOrder(newOrder);
        verify(orderMapperMock, times(1)).insert(newOrder);
    }

    @Test
    void testUpdateOrder() {
        Order updatedOrder = new Order();
        updatedOrder.setOrderId(1);
        updatedOrder.setType("rent");

        orderService.updateOrder(updatedOrder);
        verify(orderMapperMock, times(1)).update(updatedOrder);
    }

    @Test
    void testDeleteOrder() {
        Integer orderId = 1;
        orderService.deleteOrder(orderId);
        verify(orderMapperMock, times(1)).delete(orderId);
    }

    @Test
    void shouldRetrieveCorrectOrdersForGivenItemId() {
        List<Order> ordersForItem1 = orderService.retrieveOrdersByItemId(101);
        assertEquals(1, ordersForItem1.size());
        assertEquals("buy", ordersForItem1.get(0).getType());
        assertEquals(101, ordersForItem1.get(0).getItemId().intValue());

        List<Order> ordersForItem2 = orderService.retrieveOrdersByItemId(102);
        assertEquals(1, ordersForItem2.size());
        assertEquals("rent", ordersForItem2.get(0).getType());
        assertEquals(102, ordersForItem2.get(0).getItemId().intValue());
    }
}

