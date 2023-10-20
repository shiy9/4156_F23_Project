package com.ims.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemLocation {
    private Integer itemId;
    private Integer locationId;
    private Integer quantityAtLocation;
}
