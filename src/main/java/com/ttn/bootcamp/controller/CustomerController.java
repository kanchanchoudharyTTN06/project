package com.ttn.bootcamp.controller;

import com.ttn.bootcamp.dto.User.CustomerDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Object> userRegistration(@Valid @RequestBody CustomerDto customerDto) throws GenericException {
        CustomerDto user = customerService.registerUser(customerDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
