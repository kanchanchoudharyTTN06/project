package com.ttn.bootcamp.controller;

import com.ttn.bootcamp.dto.User.AddressDto;
import com.ttn.bootcamp.dto.User.CustomerDto;
import com.ttn.bootcamp.dto.User.SellerDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.model.ResetPassword;
import com.ttn.bootcamp.security.AppUser;
import com.ttn.bootcamp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/profile")
    public ResponseEntity<Object> getProfile(Authentication authentication) throws GenericException {
        CustomerDto customerDto = customerService.getSellerProfile((AppUser) authentication.getPrincipal());
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @PatchMapping("/update/profile")
    public ResponseEntity<Object> updateProfile(Authentication authentication, @RequestBody Map<String, Object> requestMap) throws GenericException {
        CustomerDto customerDto = customerService.updateProfile((AppUser) authentication.getPrincipal(), requestMap);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @PatchMapping("/update/password")
    public ResponseEntity<Object> updatePassword(Authentication authentication, @RequestBody ResetPassword resetPassword) throws GenericException {
        String response = customerService.updatePassword((AppUser) authentication.getPrincipal(), resetPassword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/update/address")
    public ResponseEntity<Object> updateAddress(Authentication authentication, @RequestBody Map<String, Object> map,
                                                @RequestParam("id") long id) throws GenericException {
        AddressDto address = customerService.updateAddress(id, map, (AppUser) authentication.getPrincipal());
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Object> deleteAddress(Authentication authentication, @PathVariable("id") long id) {
        String response = customerService.deleteAddress((AppUser) authentication.getPrincipal(), id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addresses")
    public ResponseEntity<Object> addAddress(Authentication authentication, @RequestBody AddressDto addressDto)
            throws GenericException {
        AddressDto addressDtos = customerService.addAddress((AppUser) authentication.getPrincipal(), addressDto);
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    @GetMapping("/addresses")
    public ResponseEntity<Object> getAddresses(Authentication authentication) throws GenericException {
        List<AddressDto> addressDtos = customerService.getAddresses((AppUser) authentication.getPrincipal());
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }
}
