package com.ims.mapper;

import com.ims.entity.Location;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LocationMapper {
    Location getLocationById(Integer locationId);

    int insert(Location location);

}