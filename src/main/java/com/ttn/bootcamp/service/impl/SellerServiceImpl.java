package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.Utility;
import com.ttn.bootcamp.domains.User.Role;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.dto.User.SellerDto;
import com.ttn.bootcamp.enums.UserRole;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.RoleRepository;
import com.ttn.bootcamp.repository.SellerRepository;
import com.ttn.bootcamp.service.SellerService;
import com.ttn.bootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;

    @Override
    public SellerDto registerUser(SellerDto sellerDto) throws GenericException {
        if (!sellerDto.getPassword().equals(sellerDto.getConfirmPassword())) {
            throw new GenericException("Confirm password didn't matched", HttpStatus.BAD_REQUEST);
        }

        // throws exception if email already registered
        userService.checkForEmailExist(sellerDto.getEmail());

        Seller seller = sellerDto.toSellerEntity();
        Role role = roleRepository.findByAuthority("ROLE_" + UserRole.SELLER);
        seller.setRoleList(Collections.singletonList(role));
        seller.setPassword(Utility.encrypt(seller.getPassword()));
        sellerDto = sellerRepository.save(seller).toSellerDto();

        // send account activation link
        userService.accountActivationHandler(seller);

        return sellerDto;
    }
}
