package com.example.Ems_Pro.Service;

import com.example.Ems_Pro.Entity.LocationEntity;
import com.example.Ems_Pro.Payload.Request.LocationPayload;
import org.springframework.stereotype.Service;

@Service
public interface LocationService {
    LocationEntity saveLocation(LocationPayload locationPayload);
}
