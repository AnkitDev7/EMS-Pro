package com.example.Ems_Pro.ServiceIMPL;
import com.example.Ems_Pro.Entity.DepartmentEntity;
import com.example.Ems_Pro.Entity.DepartmentLocationEntity;
import com.example.Ems_Pro.Entity.LocationEntity;
import com.example.Ems_Pro.Payload.Request.DepartmentLocationPayload;
import com.example.Ems_Pro.Payload.Response.DepartmentLocationResponse;
import com.example.Ems_Pro.Repository.DepartmentLocationRepositry;
import com.example.Ems_Pro.Repository.DepartmentRepositry;
import com.example.Ems_Pro.Repository.LocationRepositry;
import com.example.Ems_Pro.Service.DepartmentLocationService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Builder
public class DepartmentLocationServiceIMP implements DepartmentLocationService {

    @Autowired
    private DepartmentLocationRepositry departmentLocationRepositry;

    @Autowired
    private DepartmentRepositry departmentRepositry;

    @Autowired
    private LocationRepositry locationRepositry;

    private DepartmentLocationResponse mapToResponse(
            DepartmentLocationEntity departmentLocation) {

        LocationEntity location = departmentLocation.getLocation();

        return new DepartmentLocationResponse(
                departmentLocation.getDepartment().getDepartmentId(),
                location.getLocationId(),
                location.getAddress(),
                location.getCity(),
                location.getState()
        );

    }

    @Override
    public DepartmentLocationEntity saveDepartmentLocation(
            DepartmentLocationPayload departmentLocationPayload) {

       DepartmentEntity departmentEntity = departmentRepositry
               .findById(departmentLocationPayload.getDepartmentId())
               .orElseThrow(()->new RuntimeException("Department Not Found"));

       LocationEntity locationEntity = locationRepositry
               .findById(departmentLocationPayload.getLocationId())
               .orElseThrow(()->new RuntimeException("Location Not Found"));

        DepartmentLocationEntity entity = DepartmentLocationEntity.builder()
                .department(departmentEntity)
                .location(locationEntity)
                .build();

        return departmentLocationRepositry.save(entity);
    }

    @Override
    public List<DepartmentLocationResponse> getLocationsByDepartment(String departmentId) {

        return departmentLocationRepositry
                .findByDepartmentDepartmentId(departmentId)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    @Override
    public List<DepartmentLocationResponse> getAllDepartmentLocations() {

        return departmentLocationRepositry
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

    }


}
