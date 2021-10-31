package com.ttn.bootcamp.service;

import com.ttn.bootcamp.dto.User.CustomerDto;
import com.ttn.bootcamp.exceptions.GenericException;

public interface CustomerService {
    CustomerDto registerUser(CustomerDto customerDto) throws GenericException;
}
