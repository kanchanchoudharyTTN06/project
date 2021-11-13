package com.ttn.bootcamp.service;

import com.ttn.bootcamp.dto.Product.CategoryMetadataFieldValuesDto;
import com.ttn.bootcamp.exceptions.GenericException;

public interface CategoryMetadataFieldValueService {
    CategoryMetadataFieldValuesDto addOrUpdateCategoryMetadataFieldValues(CategoryMetadataFieldValuesDto categoryMetadataFieldValuesDto) throws GenericException;
}
