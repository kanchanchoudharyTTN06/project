package com.ttn.bootcamp.service;

import com.ttn.bootcamp.dto.User.CustomerDto;
import com.ttn.bootcamp.dto.User.SellerDto;
import com.ttn.bootcamp.exceptions.GenericException;

public interface SellerService {
    SellerDto registerUser(SellerDto sellerDto) throws GenericException;
}
