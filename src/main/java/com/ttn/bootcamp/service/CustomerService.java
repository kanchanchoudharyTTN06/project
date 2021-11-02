package com.ttn.bootcamp.service;

import com.ttn.bootcamp.domains.User.Customer;
import com.ttn.bootcamp.dto.User.CustomerDto;
import com.ttn.bootcamp.exceptions.GenericException;

import java.util.List;

public interface CustomerService {
    CustomerDto registerUser(CustomerDto customerDto) throws GenericException;
    List <Customer> findAllCustomers() throws GenericException;
}
