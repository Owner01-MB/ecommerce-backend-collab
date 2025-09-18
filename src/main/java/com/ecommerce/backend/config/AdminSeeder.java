package com.ecommerce.backend.config;

import com.ecommerce.backend.enums.Role;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminSeeder {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Bean
  public CommandLineRunner seedAdmin() {
    return args -> {
      if (userRepository.findByEmail("admin@smartcart.com").isEmpty()) {
        User admin = User.builder()
            .name("Super Admin")
            .email("admin@smartcart.com")
            .password(passwordEncoder.encode("admin123"))
            .role(Role.ADMIN)
            .isActive(true)
            .build();

        userRepository.save(admin);
        System.out.println("✅ Default ADMIN created: admin@smartcart.com / admin123");
      }
    };
  }
}
