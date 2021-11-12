package com.ttn.bootcamp.service;

import com.ttn.bootcamp.domains.Product.CategoryMetadataField;
import com.ttn.bootcamp.dto.Product.CategoryMetadataFieldDto;
import com.ttn.bootcamp.exceptions.GenericException;

import java.util.List;

public interface CategoryMetadataFieldService {
    CategoryMetadataFieldDto addCategoryMetadataField(CategoryMetadataFieldDto categoryMetadataFieldDto) throws GenericException;

    List<CategoryMetadataField> findAllCategoryMetadataFields() throws GenericException;
}
