package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.User.*;
import com.ttn.bootcamp.dto.User.AddressDto;
import com.ttn.bootcamp.dto.User.CustomerDto;
import com.ttn.bootcamp.dto.User.UserDto;
import com.ttn.bootcamp.enums.UserRole;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.model.ResetPassword;
import com.ttn.bootcamp.repository.CustomerRepository;
import com.ttn.bootcamp.repository.RoleRepository;
import com.ttn.bootcamp.security.AppUser;
import com.ttn.bootcamp.service.AddressService;
import com.ttn.bootcamp.service.CustomerService;
import com.ttn.bootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private RoleRepository roleRepository;
    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;
    private AddressService addressService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, RoleRepository roleRepository, UserService userService,
                               BCryptPasswordEncoder passwordEncoder, AddressService addressService) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.addressService = addressService;
    }

    @Override
    public CustomerDto registerCustomer(CustomerDto customerDto) throws GenericException {
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

        List<Address> addresses = customer.getAddressList();
        for (Address address : addresses) {
            address.setUser(customer);
            addressService.addAddress(address.toAddressDto());
        }

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

    @Override
    public CustomerDto getCustomerProfile(AppUser user) throws GenericException {
        Optional<Customer> customer = customerRepository.findByEmail(user.getUsername());
        if (customer.isPresent())
            return customer.get().toCustomerDto();
        throw new GenericException("No content found", HttpStatus.NOT_FOUND);
    }

    @Override
    public CustomerDto updateProfile(AppUser user, Map<String, Object> requestMap) throws GenericException {
        Optional<Customer> customer = customerRepository.findByEmail(user.getUsername());
        if (customer.isPresent()) {
            CustomerDto customerDto = customer.get().toCustomerDto();
            List<AddressDto> addressDtos = customerDto.getAddressList();
            requestMap.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(CustomerDto.class, key);
                Objects.requireNonNull(field).setAccessible(true);
                ReflectionUtils.setField(field, customerDto, value);
            });
            customerDto.setAddressList(addressDtos);
            return customerRepository.save(customerDto.toCustomerEntity()).toCustomerDto();
        }
        throw new GenericException("No content found", HttpStatus.NOT_FOUND);
    }

    @Override
    public String updatePassword(AppUser user, ResetPassword resetPassword) throws GenericException {
        resetPassword.setEmail(user.getUsername());
        resetPassword.setToken("not-applicable");
        return userService.updatePassword(resetPassword);
    }

    @Override
    public AddressDto updateAddress(long id, Map<String, Object> requestMap, AppUser user) throws GenericException {
        return addressService.updateAddress(id, requestMap);
    }

    @Override
    public String deleteAddress(AppUser user, long id) {
        return addressService.deleteAddress(id);
    }

    @Override
    public List<AddressDto> getAddresses(AppUser user) throws GenericException {
        return userService.getAddressesByUserEmail(user.getUsername());
    }

    @Override
    public AddressDto addAddress(AppUser user, AddressDto addressDto) throws GenericException {
        UserDto userDto = userService.getUserByEmail(user.getUsername());
        addressDto.setUser(userDto);
        return addressService.addAddress(addressDto);
    }
}
