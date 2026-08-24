package com.example.Ems_Pro.ServiceIMPL;
import com.example.Ems_Pro.Entity.RoleEntity;
import com.example.Ems_Pro.Payload.Request.RolePayload;
import com.example.Ems_Pro.Payload.Response.RoleResponse;
import com.example.Ems_Pro.Repository.RoleRepositry;
import com.example.Ems_Pro.Service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceIMP implements RoleService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepositry roleRepositry;

    private RoleResponse mapToRoleResponse(RoleEntity role) {
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setRoleId(role.getRoleId());
        roleResponse.setRoleName(role.getRoleName());
        return roleResponse;
    }

    @Override
    public RoleEntity saveRole(RolePayload rolePayload) {
        RoleEntity roleEntity = modelMapper.map(rolePayload, RoleEntity.class);
        RoleEntity saveRole = roleRepositry.save(roleEntity);
        return saveRole;
    }

    @Override
    public RoleEntity deleteRole(String id) {

        Optional<RoleEntity> byId = roleRepositry.findById(id);

        if (byId.isPresent()) {
            RoleEntity roleEntity = byId.get();
            roleRepositry.delete(roleEntity);
            return roleEntity;
        }

        throw new RuntimeException("Role not found with id: " + id);
    }

    @Override
    public RoleEntity updateRole(String id, RolePayload rolePayload) {

        Optional<RoleEntity> byId = roleRepositry.findById(id);

        if (byId.isPresent()) {
          RoleEntity roleEntity =  modelMapper.map(rolePayload,RoleEntity.class);
            RoleEntity updateRole = roleRepositry.save(roleEntity);
            return updateRole;
        }
        throw new RuntimeException("Role not found with id: " + id);
    }

    @Override
    public RoleEntity readRoleById(String id) {
        Optional<RoleEntity> byId = roleRepositry.findById(id);

        if(byId.isPresent()){
            RoleEntity roleEntity = byId.get();
            return roleEntity;
        }

        throw new RuntimeException("Role not found with id: " + id);
    }

    @Override
    public List<RoleResponse> readAllRole() {
        List<RoleEntity> allRole = roleRepositry.findAll();

        List<RoleResponse> allRoleList = allRole
                .stream()
                .map(this::mapToRoleResponse)
                .toList();

        return allRoleList;
    }

}
