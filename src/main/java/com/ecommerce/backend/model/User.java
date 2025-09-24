package com.ecommerce.backend.model;

import com.ecommerce.backend.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(unique = true, nullable = false, length = 150)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(length = 15)
  private String phone;

  @Enumerated(EnumType.STRING)   // store enum as String in DB
  @Column(nullable = false, length = 20)
  private Role role;

  @Column(nullable = false)
  private Boolean isActive = true;

  private LocalDateTime lastLoggedIn;
}

