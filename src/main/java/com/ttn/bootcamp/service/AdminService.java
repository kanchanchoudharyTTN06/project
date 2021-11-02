package com.ttn.bootcamp.service;

import com.ttn.bootcamp.domains.User.Customer;
import com.ttn.bootcamp.domains.User.Seller;

import java.util.List;

public interface AdminService {
    List<Customer> getAllCustomers();

    List<Seller> getAllSellers();
}
