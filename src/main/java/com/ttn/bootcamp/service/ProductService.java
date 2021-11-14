package com.ttn.bootcamp.service;

import com.ttn.bootcamp.domains.Product.Product;
import com.ttn.bootcamp.dto.Product.CategoryDto;
import com.ttn.bootcamp.dto.Product.ProductDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.security.AppUser;

import java.util.List;

public interface ProductService {
    ProductDto addOrUpdateProduct(ProductDto productDto, AppUser principal) throws GenericException;

    List<ProductDto> getAllProductsForAdmin(AppUser principal) throws GenericException;

    List<ProductDto> getAllProductsForAdmin() throws GenericException;

    List<ProductDto> getAllProducts() throws GenericException;

    String activateProduct(long id) throws GenericException;

    String deleteProduct(AppUser principal, long id);

    ProductDto getProductById(AppUser principal, long id) throws GenericException;

    Product getProductByIdAdmin(long id) throws GenericException;

    Product getProductById(long id) throws GenericException;

    String deactivateProduct(long id) throws GenericException;

    List<ProductDto> getSimilarProductsForId(long id) throws GenericException;

    List<CategoryDto> getFilteredCategory(String filterBy, String filterValue) throws GenericException;
}
