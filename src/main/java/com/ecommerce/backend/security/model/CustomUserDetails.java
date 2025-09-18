package com.ecommerce.backend.security.model;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

   private final User user; // wrap your User entity

  public CustomUserDetails(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // Spring Security expects "ROLE_" prefix
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail(); // login using email
  }

  @Override
  public boolean isAccountNonExpired() {
    return true; // you can extend with expiry logic if needed
  }

  @Override
  public boolean isAccountNonLocked() {
    return true; // add logic if you want to lock accounts
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true; // implement if password expiration is needed
  }

  @Override
  public boolean isEnabled() {
    return user.getIsActive(); // use your "isActive" field
  }

  // Optional: expose original user if needed
  public User getUser() {
    return user;
  }
}
