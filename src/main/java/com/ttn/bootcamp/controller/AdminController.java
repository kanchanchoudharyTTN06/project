package com.ttn.bootcamp.controller;

import com.ttn.bootcamp.domains.User.Customer;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/all/customers")
    public ResponseEntity<Object> getAllCustomers() throws GenericException {
        List<Customer> customer = adminService.getAllCustomers();
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/all/sellers")
    public ResponseEntity<Object> getAllSellers() throws GenericException {
        List<Seller> seller = adminService.getAllSellers();
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PatchMapping("/activate/users")
    public ResponseEntity<Object> activateUserAccount(@RequestBody Map<String, String> request) throws GenericException {
        String response = adminService.activateUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
