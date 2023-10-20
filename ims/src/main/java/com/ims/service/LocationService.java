package com.ims.service;

import com.ims.entity.Location;

public interface LocationService {
    Location getLocationById(Integer locationId);

    String insert(Location location);
}
