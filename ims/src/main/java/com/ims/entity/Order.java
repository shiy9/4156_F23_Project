package com.ims.entity;

import java.util.Date;
import lombok.Data;

/**
 * Represents an order in the system.
 */
@Data
public class Order {
  private Integer orderId;
  private Integer clientId;
  private String type; // {“rent”, “buy”}
  private Date orderDate;
  private String orderStatus; // (In progress / Overdue/ Complete)
}

