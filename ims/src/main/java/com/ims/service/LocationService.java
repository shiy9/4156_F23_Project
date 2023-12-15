package com.ims.service;

import com.ims.entity.Location;

/**
 * The LocationService interface defines all public business behaviors for operations on the
 * Location entity model.
 */
public interface LocationService {
  Location getLocationById(Integer locationId);

  Integer insert(Location location);
}
