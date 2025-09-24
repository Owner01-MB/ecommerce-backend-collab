package com.ecommerce.backend.controller;
import com.ecommerce.backend.model.Role;
import com.ecommerce.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/saveOrUpdateRole")
    public ResponseEntity<?> saveOrUpdateRole(@RequestBody Role role){
        return new ResponseEntity<>(roleService.saveOrUpdateRole(role), HttpStatus.OK);
    }

    @GetMapping("/getAllRoles")
    public ResponseEntity<?> getAllRole(){
        return new ResponseEntity<>(roleService.getAllRole(), HttpStatus.FOUND);
    }

    @PostMapping("/deleteRole/{id}")
    public void deleteRoleById(@PathVariable Long id) throws Exception {
        roleService.deleteRoleById(id);
    }
}
