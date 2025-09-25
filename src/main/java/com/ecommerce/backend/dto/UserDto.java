package com.ecommerce.backend.dto;

import com.ecommerce.backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private Role role; // Enum
    private Boolean isActive;
    private LocalDateTime lastLoggedIn;
}
