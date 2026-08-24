package com.example.Ems_Pro.ServiceIMPL;
import com.example.Ems_Pro.Entity.CompanyEntity;
import com.example.Ems_Pro.Entity.LocationEntity;
import com.example.Ems_Pro.Entity.UsersEntity;
import com.example.Ems_Pro.Payload.Request.LocationPayload;
import com.example.Ems_Pro.Payload.Response.*;
import com.example.Ems_Pro.Repository.CompanyRepositry;
import com.example.Ems_Pro.Repository.LocationRepositry;
import com.example.Ems_Pro.Service.LocationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceIMP implements LocationService {

    @Autowired
    private ModelMapper modelMapper ;

    @Autowired
    private CompanyRepositry companyRepositry;

    @Autowired
    private LocationRepositry locationRepositry;

    private LocationResponse mapToResponse(LocationEntity location) {

        LocationResponse locationResponse = new LocationResponse();

        locationResponse.setLocationId(
                location.getLocationId()
        );

        locationResponse.setAddress(
                location.getAddress()
        );

        locationResponse.setCity(
                location.getCity()
        );

        locationResponse.setState(
                location.getState()
        );

        if (location.getCompany() != null) {

            CompanyResponse companyResponse = new CompanyResponse();

            companyResponse.setCompanyId(
                    location.getCompany().getCompanyId()
            );

            companyResponse.setCompanyName(
                    location.getCompany().getCompanyName()
            );

            companyResponse.setCompanyAddress(
                    location.getCompany().getCompanyAddress()
            );

            companyResponse.setCompanyEmail(
                    location.getCompany().getCompanyEmail()
            );

            companyResponse.setCompanyPhoneNo(
                    location.getCompany().getCompanyPhoneNo()
            );

            locationResponse.setCompany(companyResponse);
        }

        return locationResponse;
    }

    @Override
    public LocationEntity saveLocation(LocationPayload locationPayload) {

        LocationEntity location =
                modelMapper.map(locationPayload, LocationEntity.class);

        Optional<CompanyEntity> optionalCompany =
                companyRepositry.findById(locationPayload.getCompanyId());

        if (optionalCompany.isPresent()) {

            CompanyEntity company = optionalCompany.get();

            location.setCompany(company);

            LocationEntity savedLocation =
                    locationRepositry.save(location);

            return savedLocation;

        } else {

            throw new RuntimeException("Company not found");
        }
    }

    @PutMapping("/{id}")
    @Override
    public LocationEntity updateLocationDetails(Integer id, LocationPayload locationPayload) {

        Optional<LocationEntity> optionalLocation =
                locationRepositry.findById(id);

        if (optionalLocation.isEmpty()) {
            return null;
        }

        LocationEntity locationEntity = optionalLocation.get();

        locationEntity.setCity(locationPayload.getCity());
        locationEntity.setAddress(locationPayload.getAddress());
        locationEntity.setState(locationPayload.getState());

        Optional<CompanyEntity> optionalCompany =
                companyRepositry.findById(locationPayload.getCompanyId());

        if (optionalCompany.isEmpty()) {
            throw new RuntimeException("Company not found");
        }

        locationEntity.setCompany(optionalCompany.get());

        return locationRepositry.save(locationEntity);
    }

    @Override
    public void DeleteLocaionData(Integer id) {
        Optional<LocationEntity> byId = locationRepositry.findById(id);

        if (byId.isEmpty()) {
            throw new RuntimeException("Location not found");
        }
        locationRepositry.deleteById(id);

    }

    @Override
    public List<LocationResponse> readAllLocation() {
        List<LocationEntity> locations = locationRepositry.findAll();

        List<LocationResponse> Listlocations = locations
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        return Listlocations;
    }


}
