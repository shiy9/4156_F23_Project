package com.ims.entity;

import java.util.Date;
import lombok.Data;

/**
 * Represents an order details in the system.
 */
@Data
public class OrderDetail {
  private Integer orderId;
  private Integer itemId;
  private Integer quantity;
  private Double amount;
  private Date dueDate; // only if type is "rent"
}
