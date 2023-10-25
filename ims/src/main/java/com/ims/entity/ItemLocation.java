package com.ims.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The ItemLocation entity. This class will reflect the ItemLocation entity stored in the database.
 * This is a separate entity because one location can have multiple items.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemLocation {
  private Integer itemId;
  private Integer locationId;
  private Integer quantityAtLocation;
}
