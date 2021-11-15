package com.ttn.bootcamp.service;

import com.ttn.bootcamp.dto.User.AddressDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.security.AppUser;

import java.util.Map;

public interface AddressService {
    AddressDto updateAddress(long id, Map<String, Object> map, AppUser user) throws GenericException;

    String deleteAddress(long id);

    AddressDto addAddress(AddressDto addressDto);
}
