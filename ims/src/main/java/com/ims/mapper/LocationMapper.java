package com.ims.mapper;

import com.ims.entity.Location;
import org.apache.ibatis.annotations.Mapper;

/**
 * Location query interface. Specific queries used by below functions are linked in
 * ims/src/main/resources/mapper/LocationMapper.xml
 */
@Mapper
public interface LocationMapper {
  Location getLocationById(Integer locationId);

  int insert(Location location);

}