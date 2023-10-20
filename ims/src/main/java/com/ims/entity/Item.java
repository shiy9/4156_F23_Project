package com.ims.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
