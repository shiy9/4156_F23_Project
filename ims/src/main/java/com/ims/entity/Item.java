package com.ims.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Item entity. This class will reflect the Item entity stored in the database.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
  private Integer itemId;
  private Integer userId;
  private String name;
  private Integer currentStockLevel;
  private String description;
  private Float price;
  private String barcode;
}
