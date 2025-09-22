package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.UserRegistrationDto;
import com.ecommerce.backend.enums.Role;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.security.jwt.JwtService;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public AuthController(AuthenticationManager authenticationManager,
      JwtService jwtService,
      UserRepository userRepository,
      BCryptPasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/register")
  public String register(@RequestBody UserRegistrationDto dto) {
    logger.info("Registration attempt for email: {}", dto.getEmail());

    if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
      logger.warn("Registration failed: email already registered - {}", dto.getEmail());
      return "Email already registered!";
    }

    User user = User.builder()
        .name(dto.getName())
        .email(dto.getEmail())
        .password(passwordEncoder.encode(dto.getPassword()))
        .phone(dto.getPhone())
        .role(Role.USER)  // default USER
        .isActive(true)
        .build();

    userRepository.save(user);

    logger.info("User registered successfully: {}", dto.getEmail());
    return "User registered successfully!";
  }

  @PostMapping("/login")
  public Map<String, String> login(@RequestParam String email, @RequestParam String password) {
    logger.info("Login attempt for email: {}", email);

    try {
      Authentication auth = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(email, password)
      );

      String token = jwtService.generateToken(auth.getName());

      Optional<User> userOptional = userRepository.findByEmail(email);
      if (userOptional.isPresent()) {
        User user = userOptional.get();
        user.setLastLoggedIn(LocalDateTime.now());
        userRepository.save(user);
        logger.info("User logged in successfully: {}", email);
      }

      return Map.of("token", token);

    } catch (AuthenticationException e) {
      logger.error("Login failed for email: {} - {}", email, e.getMessage());
      throw new RuntimeException("Invalid username or password");
    }
  }
}
