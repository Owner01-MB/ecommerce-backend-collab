package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.model.Role;

public interface RoleImpl {
    public Object saveOrUpdateRole(Role role);
    public Object getAllRole();
    public void deleteRoleById(Long id) throws Exception;
}
