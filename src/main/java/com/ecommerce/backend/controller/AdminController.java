package com.ecommerce.backend.controller;

import com.ecommerce.backend.enums.Role;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.repository.UserRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

  private final UserRepository userRepository;

  public AdminController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PutMapping("/changeRole")
  @PreAuthorize("hasRole('ADMIN')")
  public String changeTheRole(@RequestParam String email, @RequestParam String role) {
    logger.info("Role change request: email={}, newRole={}", email, role);

    try {
      Optional<User> byEmail = userRepository.findByEmail(email);

      if (byEmail.isPresent()) {
        User user = byEmail.get();
        Role userRole = Role.valueOf(role.toUpperCase());
        user.setRole(userRole);
        userRepository.save(user);

        logger.info("Role updated successfully: email={}, newRole={}", email, userRole);
        return "Role Updated Successfully.........";
      } else {
        logger.warn("Role change failed: user not found for email={}", email);
        return "User not found!";
      }

    } catch (IllegalArgumentException e) {
      logger.error("Invalid role provided: {} for email={}", role, email);
      throw new RuntimeException("Invalid role: " + role);
    } catch (Exception e) {
      logger.error("Unexpected error while changing role for email={}: {}", email, e.getMessage(), e);
      throw new RuntimeException("Error while updating role", e);
    }
  }
}
