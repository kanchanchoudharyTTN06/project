package com.ttn.bootcamp.service;

import com.ttn.bootcamp.dto.Product.ProductDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.security.AppUser;

import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto, AppUser principal) throws GenericException;

    List<ProductDto> getAllProducts(AppUser principal) throws GenericException;

    String activateProduct(long id) throws GenericException;

    String deleteProduct(AppUser principal, long id);
}
