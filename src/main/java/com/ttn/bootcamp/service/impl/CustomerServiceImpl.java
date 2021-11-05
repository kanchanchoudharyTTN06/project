package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.util.Utility;
import com.ttn.bootcamp.domains.User.Customer;
import com.ttn.bootcamp.domains.User.Role;
import com.ttn.bootcamp.dto.User.CustomerDto;
import com.ttn.bootcamp.enums.UserRole;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.CustomerRepository;
import com.ttn.bootcamp.repository.RoleRepository;
import com.ttn.bootcamp.service.CustomerService;
import com.ttn.bootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public CustomerDto registerUser(CustomerDto customerDto) throws GenericException {
        if (!customerDto.getPassword().equals(customerDto.getConfirmPassword())) {
            throw new GenericException("Confirm password didn't matched", HttpStatus.BAD_REQUEST);
        }
        // throws exception if email already registered
        userService.checkForEmailExist(customerDto.getEmail());

        Customer customer = customerDto.toCustomerEntity();
        Optional<Role> role = roleRepository.findByAuthority("ROLE_" + UserRole.CUSTOMER);
        role.ifPresent(value -> customer.setRoleList(Collections.singletonList(value)));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerDto = customerRepository.save(customer).toCustomerDto();

        // send account activation link
        userService.accountActivationHandler(customer);

        return customerDto;
    }

    public List<Customer> findAllCustomers() throws GenericException {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty())
            throw new GenericException("No content found", HttpStatus.NOT_FOUND);
        return customers;
    }
}
