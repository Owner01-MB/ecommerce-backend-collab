package com.ecommerce.backend.security.config;

import com.ecommerce.backend.security.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

  private final CustomUserDetailsService userDetailsService;

  public ApplicationConfig(CustomUserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    logger.info("Initializing DaoAuthenticationProvider with CustomUserDetailsService and BCryptPasswordEncoder");
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    logger.info("Exposing AuthenticationManager bean");
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    logger.info("Creating BCryptPasswordEncoder bean");
    return new BCryptPasswordEncoder();
  }
}
