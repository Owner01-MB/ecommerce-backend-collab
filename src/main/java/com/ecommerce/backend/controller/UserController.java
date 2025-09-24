package com.ecommerce.backend.controller;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

        private static final Logger logger = LoggerFactory.getLogger(UserController.class);

        @Autowired
        private UserService userService;

        @PostMapping("/saveOrUpdateUser")
        public ResponseEntity<?> saveOrUpdateUser(@RequestBody User user){
            logger.info("Saving or Updating User: {}", user);
            try {
                return new ResponseEntity<>(userService.saveOrUpdateUser(user), HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error while saving/updating user: {}", user, e);
                return new ResponseEntity<>("Failed to save/update user", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/getAllUsers")
        public ResponseEntity<?> getAllUser(){
            logger.debug("Fetching all users");
            try {
                return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error while fetching all users", e);
                return new ResponseEntity<>("Failed to fetch users", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @PostMapping("/deleteUser/{id}")
        public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
            logger.warn("Deleting user with ID: {}", id);
            try {
                userService.deleteUserById(id);
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            } catch (Exception e) {
                logger.error("Error while deleting user with ID: {}", id, e);
                return new ResponseEntity<>("Failed to delete user", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
}
