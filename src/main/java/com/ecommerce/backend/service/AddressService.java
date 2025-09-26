package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.AddressDto;
import com.ecommerce.backend.model.Address;
import com.ecommerce.backend.repository.AddressRepo;
import com.ecommerce.backend.service.serviceImpl.AddressImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements AddressImpl {

    @Autowired
    private AddressRepo addressRepo;

    Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Override
    public Object saveOrUpdateAddress(AddressDto addressDto) {
        if (addressDto.getAddressId() != null && addressRepo.existsById(addressDto.getAddressId())) {
            // Update existing address
            Address existingAddress = addressRepo.findById(addressDto.getAddressId()).get();

            existingAddress.setStreet(addressDto.getStreet());
            existingAddress.setBuildingName(addressDto.getBuildingName());
            existingAddress.setCity(addressDto.getCity());
            existingAddress.setState(addressDto.getState());
            existingAddress.setCountry(addressDto.getCountry());
            existingAddress.setPincode(addressDto.getPincode());

            addressRepo.save(existingAddress);
            logger.info("Address updated successfully with ID: {}", addressDto.getAddressId());
            return "Updated Successfully!!!";

        } else {
            // Insert new address
            Address newAddress = new Address();
            newAddress.setStreet(addressDto.getStreet());
            newAddress.setBuildingName(addressDto.getBuildingName());
            newAddress.setCity(addressDto.getCity());
            newAddress.setState(addressDto.getState());
            newAddress.setCountry(addressDto.getCountry());
            newAddress.setPincode(addressDto.getPincode());

            addressRepo.save(newAddress);
            logger.info("New address inserted with id: {}", newAddress.getAddressId());
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public List<AddressDto> getAllAddress() {
        List<Address> addresses = addressRepo.findAll();
        logger.info("Fetched all addresses, count: {}", addresses.size());

        // Convert entities to DTOs using for-loop
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address address : addresses) {
            AddressDto dto = new AddressDto();
            dto.setAddressId(address.getAddressId());
            dto.setStreet(address.getStreet());
            dto.setBuildingName(address.getBuildingName());
            dto.setCity(address.getCity());
            dto.setState(address.getState());
            dto.setCountry(address.getCountry());
            dto.setPincode(address.getPincode());
            addressDtos.add(dto);
        }
        return addressDtos;
    }


    @Override
    public void deleteAddressById(Long id) throws Exception {
        logger.info("Delete requested for address ID: {}", id);
        Optional<Address> optional = addressRepo.findById(id);
        if (optional.isPresent()) {
            addressRepo.deleteById(id);
            logger.info("Address with ID {} has been deleted successfully.", id);
        } else {
            logger.error("Address not found with ID: {}", id);
            throw new Exception("Id not Found!!!");
        }
    }
}
