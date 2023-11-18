package com.ims.service.impl;

import com.ims.constants.ItemMessages;
import com.ims.entity.Location;
import com.ims.mapper.LocationMapper;
import com.ims.service.LocationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


/**
 * Implements Location-related service.
 */
@Service
public class LocationServiceImpl implements LocationService {

  @Value("${google.maps.api-key}")
  private String googleMapsApiKey;

  @Autowired
  private LocationMapper locationMapper;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public Location getLocationById(Integer locationId) {
    return locationMapper.getLocationById(locationId);
  }

  @Override
  @Transactional
  public String insert(Location location) {
    String zipCode = location.getZipCode();
    Double[] coordinates = getCoordinates(zipCode);
    if (coordinates == null) {
      return ItemMessages.INVALID_ZIP_CODE;
    }
    location.setLatitude(coordinates[0]);
    location.setLongitude(coordinates[1]);
    if (locationMapper.insert(location) > 0) {
      return ItemMessages.INSERT_SUCCESS;
    }
    return ItemMessages.INSERT_FAILURE;
  }

  /**
   * Returns the latitude and longitude of the given zip code.
   *
   * @param zipCode the zip code
   * @return the latitude and longitude of the given zip code
   */
  public Double[] getCoordinates(String zipCode) {
    String requestUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="
            + zipCode + "&key=" + googleMapsApiKey;

    RestTemplate restTemplate = new RestTemplate();
    String response = restTemplate.getForObject(requestUrl, String.class);

    JSONObject jsonResponse = new JSONObject(response);
    if (jsonResponse.getJSONArray("results").length() == 0) {
      return null;
    }

    JSONObject location = jsonResponse.getJSONArray("results")
            .getJSONObject(0)
            .getJSONObject("geometry")
            .getJSONObject("location");

    double latitude = location.getDouble("lat");
    double longitude = location.getDouble("lng");
    return new Double[]{latitude, longitude};
  }
}
