package com.ttn.bootcamp.controller;

import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.domains.Product.Product;
import com.ttn.bootcamp.dto.Product.CategoryDto;
import com.ttn.bootcamp.dto.Product.ProductDto;
import com.ttn.bootcamp.dto.User.AddressDto;
import com.ttn.bootcamp.dto.User.CustomerDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.model.ResetPassword;
import com.ttn.bootcamp.security.AppUser;
import com.ttn.bootcamp.service.CategoryService;
import com.ttn.bootcamp.service.CustomerService;
import com.ttn.bootcamp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private CustomerService customerService;
    private CategoryService categoryService;
    private ProductService productService;

    @Autowired
    public CustomerController(CustomerService customerService, CategoryService categoryService, ProductService productService) {
        this.customerService = customerService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

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
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            Category category = categoryService.findById(id.get());
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") long id) throws GenericException {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product.toProductDto(), HttpStatus.OK);
    }

    @GetMapping("/categories/{id}/products")
    public ResponseEntity<Object> getProductsInCategory(@PathVariable("id") long id) throws GenericException {
        List<ProductDto> products = categoryService.getProductsInCategory(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{id}/similarproducts")
    public ResponseEntity<Object> getSimilarProducts(@PathVariable("id") long id) throws GenericException {
        List<ProductDto> productDtos = productService.getSimilarProductsForId(id);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/filtercategory")
    public ResponseEntity<Object> getFilteredCategory(@RequestParam("filterBy") String filterBy,
                                                      @RequestParam("filterValue") String filterValue) throws GenericException {
        List<CategoryDto> categories = productService.getFilteredCategory(filterBy, filterValue);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
