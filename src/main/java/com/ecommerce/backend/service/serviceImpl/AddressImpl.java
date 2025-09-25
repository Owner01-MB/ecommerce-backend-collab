package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.dto.AddressDto;
import com.ecommerce.backend.model.Address;

import java.util.List;

public interface AddressImpl {
    Object saveOrUpdateAddress(AddressDto addressDto);
    List<AddressDto> getAllAddress();
    void deleteAddressById(Long id) throws Exception;;
}
