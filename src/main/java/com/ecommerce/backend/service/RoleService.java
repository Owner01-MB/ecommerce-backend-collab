package com.ecommerce.backend.service;

import com.ecommerce.backend.model.Role;
import com.ecommerce.backend.repository.RoleRepo;
import com.ecommerce.backend.service.serviceImpl.RoleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements RoleImpl {
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public Object saveOrUpdateRole(Role role) {
        if (role.getRoleId() != null && roleRepo.existsById(role.getRoleId())) {
            Role existingRole = roleRepo.findById(role.getRoleId()).get();

            existingRole.setRoleName(role.getRoleName());

            roleRepo.save(existingRole);
            return "Updated Successfully!!!";
        } else {
            roleRepo.save(role);
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public Object getAllRole() {
        return roleRepo.findAll();
    }

    @Override
    public void deleteRoleById(Long id) throws Exception {
        Optional<Role> optional = roleRepo.findById(id);
        if (optional.isPresent()) {
            roleRepo.deleteById(id);
        } else {
            throw new Exception("Id not Found!!!");
        }
    }
}
