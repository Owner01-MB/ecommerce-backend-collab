package com.ecommerce.backend.controller;

import com.ecommerce.backend.enums.Role;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  private final UserRepository userRepository;

  public AdminController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PutMapping("/changeRole")
  @PreAuthorize("hasRole('ADMIN')")
  public String changeTheRole(@RequestParam String email, @RequestParam String role) {
    try {
      Optional<Object> byEmail = userRepository.findByEmail(email);
      if (byEmail.isPresent()) {
        User user = (User) byEmail.get();
        Role userRole = Role.valueOf(role.toUpperCase());
        user.setRole(userRole);
        userRepository.save(user);
      }
      return "Role Updated Successfully.........";
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
