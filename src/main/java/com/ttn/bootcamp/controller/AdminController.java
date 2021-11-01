package com.ttn.bootcamp.controller;

import com.ttn.bootcamp.domains.User.Customer;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.service.CustomerService;
import com.ttn.bootcamp.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SellerService sellerService;

    @GetMapping("/all/customers")
    public ResponseEntity<Object> getAllCustomers() {
        List<Customer> customer = customerService.findAllCustomers();
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/all/sellers")
    public ResponseEntity<Object> getAllSellers() {
        List<Seller> seller = sellerService.findAllSellers();
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }
}
