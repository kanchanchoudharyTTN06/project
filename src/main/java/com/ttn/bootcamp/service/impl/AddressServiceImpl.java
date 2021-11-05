package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.User.Address;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.dto.User.AddressDto;
import com.ttn.bootcamp.dto.User.SellerDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.AddressRepository;
import com.ttn.bootcamp.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public AddressDto updateAddress(long id, Map<String, Object> map) throws GenericException {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            AddressDto addressDto = address.get().toAddressDto();
            map.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(AddressDto.class, key);
                Objects.requireNonNull(field).setAccessible(true);
                ReflectionUtils.setField(field, addressDto, value);
            });
            return addressRepository.save(addressDto.toAddressEntity()).toAddressDto();
        }
        throw new GenericException("No content found", HttpStatus.NOT_FOUND);
    }
}
