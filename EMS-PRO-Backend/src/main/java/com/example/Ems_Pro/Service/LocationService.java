package com.example.Ems_Pro.Service;

import com.example.Ems_Pro.Entity.LocationEntity;
import com.example.Ems_Pro.Payload.Request.LocationPayload;
import com.example.Ems_Pro.Payload.Response.LocationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {
    LocationEntity saveLocation(LocationPayload locationPayload);
    LocationEntity updateLocationDetails(Integer id, LocationPayload locationPayload);
    void DeleteLocaionData(Integer id);
    List<LocationResponse> readAllLocation();
}
