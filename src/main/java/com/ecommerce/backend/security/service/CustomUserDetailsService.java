package com.ecommerce.backend.security.service;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.security.model.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

  private final UserRepository userRepository;

  // constructor injection
  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.info("Attempting to load user by email: {}", username);

    User user = (User) userRepository.findByEmail(username)
        .orElseThrow(() -> {
          logger.error("User not found with email: {}", username);
          return new UsernameNotFoundException("User not found with email: " + username);
        });

    logger.debug("User found: {} with role {}", user.getEmail(), user.getRole());

    return new CustomUserDetails(user);
  }
}
