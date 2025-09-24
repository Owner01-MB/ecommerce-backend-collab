package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Address;
import com.ecommerce.backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddressController {
        @Autowired
        private AddressService addressService;

        @PostMapping("/saveOrUpdateAddress")
        public ResponseEntity<?> saveOrUpdateAddress(@RequestBody Address address){
            return new ResponseEntity<>(addressService.saveOrUpdateAddress(address), HttpStatus.OK);
        }
        @GetMapping("/getAllAddress")
        public ResponseEntity<?> getAllAddress(){
            return new ResponseEntity<>(addressService.getAllAddress(),HttpStatus.FOUND);
        }
        @PostMapping("/deleteAddress/{Id}")
        public void deleteAddressById(@PathVariable Long id) throws Exception {
            addressService.deleteAddressById(id) ;
        }
    }
