package com.example.Ems_Pro.ServiceIMPL;

import com.example.Ems_Pro.Entity.CompanyEntity;
import com.example.Ems_Pro.Entity.LocationEntity;
import com.example.Ems_Pro.Payload.Request.LocationPayload;
import com.example.Ems_Pro.Repository.CompanyRepositry;
import com.example.Ems_Pro.Repository.LocationRepositry;
import com.example.Ems_Pro.Service.LocationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationServiceIMP implements LocationService {

    @Autowired
    private ModelMapper modelMapper ;

    @Autowired
    private CompanyRepositry companyRepositry;

    @Autowired
    private LocationRepositry locationRepositry;

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
}
