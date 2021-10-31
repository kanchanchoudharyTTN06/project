package com.ttn.bootcamp.controller;

import com.ttn.bootcamp.dto.User.SellerDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/sellers")
public class SellerController {
    @Autowired
    SellerService sellerService;

    @PostMapping("/register")
    public ResponseEntity<Object> userRegistration(@Valid @RequestBody SellerDto sellerDto) throws GenericException {
        SellerDto user = sellerService.registerUser(sellerDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
