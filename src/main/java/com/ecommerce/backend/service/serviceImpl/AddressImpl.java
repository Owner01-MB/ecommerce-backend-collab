package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.model.Address;

public interface AddressImpl {
    public Object saveOrUpdateAddress(Address address);
    public Object getAllAddress();
    public void deleteAddressById(Long id) throws Exception;
}
