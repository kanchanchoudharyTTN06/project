package com.ttn.bootcamp.service;

import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.dto.Product.CategoryDto;
import com.ttn.bootcamp.dto.Product.CategoryMetadataFieldValuesDto;
import com.ttn.bootcamp.dto.Product.ProductDto;
import com.ttn.bootcamp.exceptions.GenericException;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto) throws GenericException;

    List<Category> findAllCategory() throws GenericException;

    Category findById(long id) throws GenericException;

    Category updateCategory(CategoryDto categoryDto) throws GenericException;

    List<ProductDto> getProductsInCategory(long id) throws GenericException;
}
