package com.ims.service.impl;

import com.ims.constants.ItemMessages;
import com.ims.entity.Location;
import com.ims.mapper.LocationMapper;
import com.ims.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationServiceImpl implements LocationService {

  @Autowired
  private LocationMapper locationMapper;

  @Override
  public Location getLocationById(Integer locationId) {
    return locationMapper.getLocationById(locationId);
  }

  @Override
  @Transactional
  public String insert(Location location) {
    if (locationMapper.insert(location) > 0) {
      return ItemMessages.INSERT_SUCCESS;
    }
    return ItemMessages.INSERT_FAILURE;
  }
}
