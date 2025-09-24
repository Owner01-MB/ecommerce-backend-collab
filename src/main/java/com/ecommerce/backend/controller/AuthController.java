package com.ecommerce.backend.controller;

import com.ecommerce.backend.security.jwt.JwtService;
import com.ecommerce.backend.dto.UserRegistrationDto;
import com.ecommerce.backend.enums.Role;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.repository.UserRepository;;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
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

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,
      UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/register")
  public String register(@RequestBody UserRegistrationDto dto) {
    if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
      return "Email already registered!";
    }

    User user = User.builder()
        .name(dto.getName())
        .email(dto.getEmail())
        .password(passwordEncoder.encode(dto.getPassword()))
        .phone(dto.getPhone())
        .role(Role.USER)     // ✅ Always default USER
        .isActive(true)
        .build();

    userRepository.save(user);

    return "User registered successfully!";
  }

  @PostMapping("/login")
  public Map<String, String> login(@RequestParam String email, @RequestParam String password) {
    try {
      Authentication auth = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(email, password)
      );

      String token = jwtService.generateToken(auth.getName());
      Optional<Object> user = userRepository.findByEmail(email);
      if (user.isPresent()) {
        User user1 = (User) user.get();
        user1.setLastLoggedIn(LocalDateTime.now());
        userRepository.save(user1);

      }

      return Map.of("token", token);

    } catch (AuthenticationException e) {
      throw new RuntimeException("Invalid username or password");
    }
  }
}
