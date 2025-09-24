package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalTime;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/saveOrUpdateUser")
  public ResponseEntity<?> saveOrUpdateUser(@RequestBody User user) {
    return new ResponseEntity<>(userService.saveOrUpdateUser(user), HttpStatus.OK);
  }

  @GetMapping("/getAllUsers")
  public ResponseEntity<?> getAllUser() {
    return new ResponseEntity<>(userService.getAllUser(), HttpStatus.FOUND);
  }

  @PostMapping("/deleteUser/{id}")
  public void deleteUserById(@PathVariable Long id) throws Exception {
    userService.deleteUserById(id);
  }

}
