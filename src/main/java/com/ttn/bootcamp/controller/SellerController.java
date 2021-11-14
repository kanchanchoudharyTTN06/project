package com.ttn.bootcamp.controller;

import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.domains.Product.ProductVariation;
import com.ttn.bootcamp.dto.Product.ProductDto;
import com.ttn.bootcamp.dto.Product.ProductVariationDto;
import com.ttn.bootcamp.dto.User.AddressDto;
import com.ttn.bootcamp.dto.User.SellerDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.model.ResetPassword;
import com.ttn.bootcamp.security.AppUser;
import com.ttn.bootcamp.service.CategoryService;
import com.ttn.bootcamp.service.ProductService;
import com.ttn.bootcamp.service.ProductVariationService;
import com.ttn.bootcamp.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sellers")
public class SellerController {
    @Autowired
    SellerService sellerService;
    @Autowired
    private ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductVariationService productVariationService;

    @PostMapping("/register")
    public ResponseEntity<Object> userRegistration(@Valid @RequestBody SellerDto sellerDto) throws GenericException {
        SellerDto user = sellerService.registerSeller(sellerDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> getProfile(Authentication authentication) throws GenericException {
        SellerDto sellerDto = sellerService.getSellerProfile((AppUser) authentication.getPrincipal());
        return new ResponseEntity<>(sellerDto, HttpStatus.OK);
    }

    @PatchMapping("/update/profile")
    public ResponseEntity<Object> updateProfile(Authentication authentication, @RequestBody Map<String, Object> requestMap) throws GenericException {
        SellerDto sellerDto = sellerService.updateProfile((AppUser) authentication.getPrincipal(), requestMap);
        return new ResponseEntity<>(sellerDto, HttpStatus.OK);
    }

    @PatchMapping("/update/password")
    public ResponseEntity<Object> updatePassword(Authentication authentication, @RequestBody ResetPassword resetPassword) throws GenericException {
        String response = sellerService.updatePassword((AppUser) authentication.getPrincipal(), resetPassword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/update/address")
    public ResponseEntity<Object> updateAddress(Authentication authentication, @RequestBody Map<String, Object> map,
                                                @RequestParam("id") long id) throws GenericException {
        AddressDto address = sellerService.updateAddress(id, map, (AppUser) authentication.getPrincipal());
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PostMapping("/add/product")
    public ResponseEntity<Object> addProduct(@Valid @RequestBody ProductDto productDto, Authentication authentication) throws GenericException {
        ProductDto productDtos = productService.addOrUpdateProduct(productDto, (AppUser) authentication.getPrincipal());
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @PutMapping("/update/product")
    public ResponseEntity<Object> updateProduct(@Valid @RequestBody ProductDto productDto, Authentication authentication) throws GenericException {
        if (productDto.getId() == 0)
            throw new GenericException("Product id is mandatory", HttpStatus.BAD_REQUEST);

        ProductDto productDtos = productService.addOrUpdateProduct(productDto, (AppUser) authentication.getPrincipal());
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/all/products")
    public ResponseEntity<Object> getAllProducts(Authentication authentication) throws GenericException {
        List<ProductDto> productDto = productService.getAllProducts((AppUser) authentication.getPrincipal());
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(Authentication authentication, @PathVariable("id") long id) {
        String response = productService.deleteProduct((AppUser) authentication.getPrincipal(), id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getProductById(Authentication authentication, @PathVariable("id") long id) throws GenericException {
        ProductDto response = productService.getProductById((AppUser) authentication.getPrincipal(), id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/categories")
    public ResponseEntity<Object> getAllCategory() throws GenericException {
        List<Category> categories = categoryService.findAllCategory();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/add/product/variation")
    public ResponseEntity<Object> addProductVariation(@Valid @RequestBody ProductVariationDto productVariationDto) throws GenericException {
        ProductVariationDto productVariation = productVariationService.addProductVariation(productVariationDto);
        return new ResponseEntity<>(productVariation, HttpStatus.OK);
    }
}
