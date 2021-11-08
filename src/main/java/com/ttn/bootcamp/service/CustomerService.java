package com.ttn.bootcamp.service;

import com.ttn.bootcamp.domains.User.Customer;
import com.ttn.bootcamp.dto.User.AddressDto;
import com.ttn.bootcamp.dto.User.CustomerDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.model.ResetPassword;
import com.ttn.bootcamp.security.AppUser;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    CustomerDto registerCustomer(CustomerDto customerDto) throws GenericException;

    List<Customer> findAllCustomers() throws GenericException;

    CustomerDto getCustomerProfile(AppUser user) throws GenericException;

    CustomerDto updateProfile(AppUser user, Map<String, Object> requestMap) throws GenericException;

    String updatePassword(AppUser user, ResetPassword resetPassword) throws GenericException;

    AddressDto updateAddress(long id, Map<String, Object> requestMap, AppUser user) throws GenericException;

    String deleteAddress(AppUser user, long id);

    List<AddressDto> getAddresses(AppUser user) throws GenericException;

    AddressDto addAddress(AppUser user, AddressDto addressDto) throws GenericException;
}
