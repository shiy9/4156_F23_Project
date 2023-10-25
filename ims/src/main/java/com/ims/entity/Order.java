package com.ims.entity;

import java.util.Date;
import lombok.Data;

/**
 * Represents an order in the system.
 */
@Data
public class Order {
  private Integer orderId;
  private Integer userId;
  private String type; // {“rent”, “buy”}
  private Integer itemId;
  private Integer itemLocationId;
  private Integer quantity;
  private Date orderDate;
  private Double amount;
  private Date dueDate; // only if type is "rent"
  private Date returnDate;
  private String orderStatus; // (In progress / Overdue/ Complete)

}

