package com.ttn.bootcamp.service;

import com.ttn.bootcamp.domains.User.Customer;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.exceptions.GenericException;

import java.util.List;
import java.util.Map;

public interface AdminService {
    List<Customer> getAllCustomers() throws GenericException;

    List<Seller> getAllSellers() throws GenericException;

    String activateUser(Map<String, String> request) throws GenericException;

    String deActivateUser(Map<String, String> request) throws GenericException;
}
