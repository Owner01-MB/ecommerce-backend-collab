package com.ecommerce.backend.service;

import com.ecommerce.backend.model.Address;
import com.ecommerce.backend.repository.AddressRepo;
import com.ecommerce.backend.service.serviceImpl.AddressImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService implements AddressImpl {

  @Autowired
  private AddressRepo addressRepo;

  @Override
  public Object saveOrUpdateAddress(Address address) {
    if (address.getAddressId() != null && addressRepo.existsById(address.getAddressId())) {
      Address existingAddress = addressRepo.findById(address.getAddressId()).get();

      existingAddress.setStreet(address.getStreet());
      existingAddress.setBuildingName(address.getBuildingName());
      existingAddress.setCity(address.getCity());
      existingAddress.setState(address.getState());
      existingAddress.setCountry(address.getCountry());
      existingAddress.setPincode(address.getPincode());

      addressRepo.save(existingAddress);
      return "Updated Successfully!!!";
    } else {
      addressRepo.save(address);
      return "Inserted Successfully!!!";
    }
  }

  @Override
  public Object getAllAddress() {
    return addressRepo.findAll();
  }

  @Override
  public void deleteAddressById(Long id) throws Exception {
    Optional<Address> optional = addressRepo.findById(id);
    if (optional.isPresent()) {
      addressRepo.deleteById(id);
    } else {
      throw new Exception("Id not Found!!!");
    }
  }
}
