package com.example.Ems_Pro.ServiceIMPL;
import com.example.Ems_Pro.Entity.DepartmentEntity;
import com.example.Ems_Pro.Entity.DepartmentManager;
import com.example.Ems_Pro.Entity.UsersEntity;
import com.example.Ems_Pro.Payload.Request.DepartmentManagerPayload;
import com.example.Ems_Pro.Payload.Response.DepartmentManagerResponse;
import com.example.Ems_Pro.Repository.DepartmentManagerRepositry;
import com.example.Ems_Pro.Repository.DepartmentRepositry;
import com.example.Ems_Pro.Repository.UserRepositry;
import com.example.Ems_Pro.Service.DepartmentManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentManagerServiceIMP implements DepartmentManagerService {

    @Autowired
    private DepartmentManagerRepositry departmentManagerRepositry;

    @Autowired
    private DepartmentRepositry departmentRepositry;

    @Autowired
    private UserRepositry userRepositry;


    private DepartmentManagerResponse maptoResponse(DepartmentManager departmentManager) {

        DepartmentManagerResponse departmentManagerResponse = new DepartmentManagerResponse();

        departmentManagerResponse.setDepartmentId(
                departmentManager.getDepartment().getDepartmentId()
        );

        departmentManagerResponse.setDepartmentName(
                departmentManager.getDepartment().getDepartmentName()
        );

        departmentManagerResponse.setUserId(
                departmentManager.getUser().getUserId()
        );

        departmentManagerResponse.setUserName(
                departmentManager.getUser().getName()
        );

        departmentManagerResponse.setAssignedDate(
                departmentManager.getAssignedDate()
        );

        departmentManagerResponse.setEndDate(
                departmentManager.getEndDate()
        );

        return departmentManagerResponse;
    }


    @Override
    public DepartmentManagerResponse addDepartmentManager(
            DepartmentManagerPayload departmentManagerPayload) {

        DepartmentEntity department = departmentRepositry
                .findById(departmentManagerPayload.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException("Department not found"));

        UsersEntity user = userRepositry
                .findById(departmentManagerPayload.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        DepartmentManager departmentManager = DepartmentManager.builder()
                .department(department)
                .user(user)
                .assignedDate(departmentManagerPayload.getAssignedDate())
                .endDate(departmentManagerPayload.getEndDate())
                .build();

        departmentManagerRepositry.save(departmentManager);

        return maptoResponse(departmentManager);
    }


    @Override
    public DepartmentManagerResponse updateDepartmentManager(
            DepartmentManagerPayload departmentManagerPayload) {

        DepartmentEntity department = departmentRepositry
                .findById(departmentManagerPayload.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException("Department not found"));

        DepartmentManager departmentManager =
                departmentManagerRepositry.findById(department)
                        .orElseThrow(() ->
                                new RuntimeException("Department Manager not found"));

        UsersEntity user = userRepositry
                .findById(departmentManagerPayload.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        departmentManager.setUser(user);

        departmentManager.setAssignedDate(
                departmentManagerPayload.getAssignedDate()
        );

        departmentManager.setEndDate(
                departmentManagerPayload.getEndDate()
        );

        departmentManagerRepositry.save(departmentManager);

        return maptoResponse(departmentManager);
    }


    @Override
    public void deleteDepartmentManager(String departmentId) {

        DepartmentEntity department = departmentRepositry
                .findById(departmentId)
                .orElseThrow(() ->
                        new RuntimeException("Department not found"));

        if (!departmentManagerRepositry.existsById(department)) {
            throw new RuntimeException("Department Manager not found");
        }

        departmentManagerRepositry.deleteById(department);
    }


}
