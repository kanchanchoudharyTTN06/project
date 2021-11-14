package com.ttn.bootcamp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.dto.User.AddressDto;
import com.ttn.bootcamp.dto.User.CustomerDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.model.ResetPassword;
import com.ttn.bootcamp.security.AppUser;
import com.ttn.bootcamp.service.CategoryService;
import com.ttn.bootcamp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/register")
    public ResponseEntity<Object> userRegistration(@Valid @RequestBody CustomerDto customerDto) throws GenericException {
        CustomerDto user = customerService.registerCustomer(customerDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> getProfile(Authentication authentication) throws GenericException {
        CustomerDto customerDto = customerService.getCustomerProfile((AppUser) authentication.getPrincipal());
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

    @GetMapping(value = {"/all/categories", "/all/categories/{id}"})
    public ResponseEntity<Object> getCategories(@PathVariable(required = false) Optional<Long> id) throws GenericException {
        if (!id.isPresent()) {
            List<Category> list = categoryService.findAllCategory();
            /*Map<Object, List<Category>> result = list.stream().collect(Collectors.groupingBy(c->c.getParentCategory()));
            List<Map<String, Object>> mapList = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            list.forEach(category -> {
                Map<String, Object> map = mapper.convertValue(category, new TypeReference<Map<String, Object>>() {
                });
                map.keySet().removeIf(k -> !(k.equals("id") || k.equals("name")));
                mapList.add(map);
            });*/
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            Category category = categoryService.findById(id.get());
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
    }
}
