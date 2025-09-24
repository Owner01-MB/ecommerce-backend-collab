package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Address;
import com.ecommerce.backend.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddressController {
    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    @PostMapping("/saveOrUpdateAddress")
    public ResponseEntity<?> saveOrUpdateAddress(@RequestBody Address address){
        logger.info("Saving or Updating Address: {}", address);  // INFO log
        try {
            return new ResponseEntity<>(addressService.saveOrUpdateAddress(address), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving address", e);  // ERROR log
            return new ResponseEntity<>("Failed to save address", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllAddress")
    public ResponseEntity<?> getAllAddress(){
        logger.debug("Fetching all addresses");  // DEBUG log
        return new ResponseEntity<>(addressService.getAllAddress(), HttpStatus.OK);
    }

    @PostMapping("/deleteAddress/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable Long id) {
        logger.warn("Deleting address with ID: {}", id);  // WARN log
        try {
            addressService.deleteAddressById(id);
            return new ResponseEntity<>("Address deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting address with ID: {}", id, e);
            return new ResponseEntity<>("Failed to delete address", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    }
