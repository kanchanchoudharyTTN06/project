package com.ttn.bootcamp.service;

import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.dto.User.SellerDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.security.AppUser;

import java.util.List;
import java.util.Map;

public interface SellerService {
    SellerDto registerUser(SellerDto sellerDto) throws GenericException;

    List<Seller> findAllSellers() throws GenericException;

    SellerDto getSellerProfile(AppUser user) throws GenericException;

    SellerDto updateProfile(AppUser user, Map<String, Object> requestMap) throws GenericException;
}
