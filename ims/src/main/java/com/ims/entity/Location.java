package com.ims.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Location class represents the Locations entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
  private Integer locationId;
  private String name;
  private String locationType;
  private String address1;
  private String address2;
  private String clientId;
  private String zipCode;
  private Double latitude;
  private Double longitude;
}
