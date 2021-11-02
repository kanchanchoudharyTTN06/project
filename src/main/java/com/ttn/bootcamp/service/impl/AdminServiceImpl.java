package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.User.Customer;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.service.AdminService;
import com.ttn.bootcamp.service.CustomerService;
import com.ttn.bootcamp.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SellerService sellerService;

    @Override
    public List<Customer> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @Override
    public List<Seller> getAllSellers() {
        return sellerService.findAllSellers();
    }
}
