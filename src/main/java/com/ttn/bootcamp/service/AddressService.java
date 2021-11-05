package com.ttn.bootcamp.service;

import com.ttn.bootcamp.dto.User.AddressDto;
import com.ttn.bootcamp.exceptions.GenericException;

import java.util.Map;

public interface AddressService {
    AddressDto updateAddress(long id, Map<String, Object> map) throws GenericException;
}
