package com.ttn.bootcamp.service;

import com.ttn.bootcamp.domains.User.Customer;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.dto.User.CustomerDto;
import com.ttn.bootcamp.dto.User.SellerDto;
import com.ttn.bootcamp.exceptions.GenericException;

import java.util.List;

public interface SellerService {
    SellerDto registerUser(SellerDto sellerDto) throws GenericException;
    List<Seller> findAllSellers();
}
