package com.ecommerce.backend.controller;
import com.ecommerce.backend.dto.UserDto;
import com.ecommerce.backend.dto.request.LogInRequest;
import com.ecommerce.backend.dto.request.OTPRequest;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/saveOrUpdateUser")
    public ResponseEntity<?> saveOrUpdateUser(@RequestBody UserDto userDto) {
        logger.info("Received request to saveOrUpdate user: {}", userDto);
        try {
            Object response = userService.saveOrUpdateUser(userDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving/updating user: {}", userDto, e);
            return new ResponseEntity<>("Error while saving/updating user: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        logger.info("Received request to fetch all users");
        try {
            List<UserDto> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching users", e);
            return new ResponseEntity<>("Error while fetching users: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        logger.info("Received request to delete user with ID: {}", id);
        try {
            userService.deleteUserById(id);
            logger.info("Deleted user successfully with ID: {}", id);
            return new ResponseEntity<>("Deleted Successfully!!!", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting user with ID: {}", id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/logIn")
    public ResponseEntity<?> logIn(@RequestBody LogInRequest logInRequest) {
        logger.info("Received login request for email: {}", logInRequest.getEmail());
        try {
            Object response = userService.logIn(logInRequest);
            logger.info("Login successful for email: {}", logInRequest.getEmail());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error during login for email: {}", logInRequest.getEmail(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/checkOTP")
    public ResponseEntity<?> checkOTP(@RequestBody OTPRequest otpRequest) {
        logger.info("Received OTP verification request: {}", otpRequest.getOtp());
        try {
            Object response = userService.checkOTP(otpRequest);
            logger.info("OTP verification successful: {}", otpRequest.getOtp());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error during OTP verification: {}", otpRequest.getOtp(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

}
