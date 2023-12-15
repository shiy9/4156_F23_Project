package com.ims.entity;

import java.util.Date;
import lombok.Data;

/**
 * OrderJoinOrderDetail entity for client application display.
 */
@Data
public class OrderJoinOrderDetail {
  private Integer orderId;
  private Integer itemId;
  private Integer locationId;
  private Integer quantity;
  private Double amount;
  private Date dueDate; // only if type is "rent"
  private Integer clientId;
  private String type; // {“rent”, “buy”}
  private Date orderDate;
  private String orderStatus; // (In progress / Overdue/ Complete)
}
