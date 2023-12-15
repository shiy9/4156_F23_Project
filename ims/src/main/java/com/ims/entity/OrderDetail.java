package com.ims.entity;

import lombok.Data;

import java.util.Date;

/**
 * Represents an order details in the system.
 */
@Data
public class OrderDetail {
  private Integer orderId;
  private Integer itemId;
  private Integer locationId;
  private Integer quantity;
  private Double amount;
  private Date dueDate; // only if type is "rent"

}
