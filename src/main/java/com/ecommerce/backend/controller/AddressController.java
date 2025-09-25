package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.AddressDto;
import com.ecommerce.backend.model.Address;
import com.ecommerce.backend.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @PostMapping("/saveOrUpdateAddress")
    public ResponseEntity<?> saveOrUpdateAddress(@RequestBody AddressDto addressDto) {
        logger.info("Received request to saveOrUpdate address: {}", addressDto);
        try {
            Object response = addressService.saveOrUpdateAddress(addressDto);
            logger.info("Response from saveOrUpdateAddress: {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while saving/updating address: {}", addressDto, e);
            return new ResponseEntity<>("Error while saving/updating address: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllAddress")
    public ResponseEntity<?> getAllAddress() {
        logger.info("Received request to fetch all addresses");
        try {
            List<AddressDto> addresses = addressService.getAllAddress(); // DTO list return
            logger.info("Fetched addresses successfully, count: {}", addresses.size());
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching addresses", e);
            return new ResponseEntity<>("Error while fetching addresses: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteAddress/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable Long id) {
        logger.info("Received request to delete address with ID: {}", id);
        try {
            addressService.deleteAddressById(id);
            logger.info("Deleted address successfully with ID: {}", id);
            return new ResponseEntity<>("Deleted Successfully!!!", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while deleting address with ID: {}", id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    }
