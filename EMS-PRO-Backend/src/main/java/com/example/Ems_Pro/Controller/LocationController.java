package com.example.Ems_Pro.Controller;
import com.example.Ems_Pro.Entity.LocationEntity;
import com.example.Ems_Pro.Payload.Request.LocationPayload;
import com.example.Ems_Pro.Payload.Response.LocationResponse;
import com.example.Ems_Pro.Service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/locations")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> saveLocation( @Valid
            @RequestBody LocationPayload locationPayload) {

        LocationEntity location =
                locationService.saveLocation(locationPayload);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Location saved successfully",
                        "location", location
                ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateLocationDetails(@PathVariable Integer id,
            @RequestBody LocationPayload locationPayload) {

      LocationEntity locationEntity =  locationService.updateLocationDetails(id,locationPayload);

      if (locationEntity != null) {
          return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("Sucessfully updated location",locationEntity));
      }

      return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Map.of("message", "Location not found"));

    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Integer id) {
        locationService.DeleteLocaionData(id);
        return ResponseEntity.ok("Location deleted successfully");
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllLocationDetails() {
       List<LocationResponse> list = locationService.readAllLocation();

       if (list.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of
                   ("message", "Location not found")
           );
       }
       return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}