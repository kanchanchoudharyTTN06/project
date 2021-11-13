package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.Product.CategoryMetadataFieldKey;
import com.ttn.bootcamp.domains.Product.CategoryMetadataFieldValues;
import com.ttn.bootcamp.dto.Product.CategoryMetadataFieldValuesDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.CategoryMetadataFieldValuesRepository;
import com.ttn.bootcamp.service.CategoryMetadataFieldService;
import com.ttn.bootcamp.service.CategoryMetadataFieldValueService;
import com.ttn.bootcamp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoryMetadataFieldValueServiceImpl implements CategoryMetadataFieldValueService {

    private CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository;
    private CategoryMetadataFieldService categoryMetadataFieldService;
    private CategoryService categoryService;

    @Autowired
    public CategoryMetadataFieldValueServiceImpl(CategoryMetadataFieldValuesRepository categoryMetadataFieldValuesRepository, CategoryMetadataFieldService categoryMetadataFieldService, CategoryService categoryService) {
        this.categoryMetadataFieldValuesRepository = categoryMetadataFieldValuesRepository;
        this.categoryMetadataFieldService = categoryMetadataFieldService;
        this.categoryService = categoryService;
    }

    @Override
    public CategoryMetadataFieldValuesDto addOrUpdateCategoryMetadataFieldValues(CategoryMetadataFieldValuesDto categoryMetadataFieldValuesDto) throws GenericException {
        validateCategoryMetadataFieldValuesRequest(categoryMetadataFieldValuesDto);

        CategoryMetadataFieldValues categoryMetadataFieldValues = new CategoryMetadataFieldValues();
        CategoryMetadataFieldKey categoryMetadataFieldKey =
                new CategoryMetadataFieldKey(categoryMetadataFieldValuesDto.getCategoryMetadataFieldId(), categoryMetadataFieldValuesDto.getCategoryId());
        categoryMetadataFieldValues.setCategoryMetadataFieldKey(categoryMetadataFieldKey);
        categoryMetadataFieldValues.setValuesList(String.join(", ", categoryMetadataFieldValuesDto.getValuesList()));
        categoryMetadataFieldValues = categoryMetadataFieldValuesRepository.save(categoryMetadataFieldValues);

        return categoryMetadataFieldValues.toCategoryMetadataFieldValuesDto();
    }

    private void validateCategoryMetadataFieldValuesRequest(CategoryMetadataFieldValuesDto categoryMetadataFieldValuesDto) throws GenericException {
        if (categoryMetadataFieldValuesDto.getCategoryMetadataFieldId() == 0) {
            throw new GenericException("CategoryMetadataFieldId is mandatory", HttpStatus.BAD_REQUEST);
        } else {
            categoryMetadataFieldService.findById(categoryMetadataFieldValuesDto.getCategoryMetadataFieldId());
        }
        if (categoryMetadataFieldValuesDto.getCategoryId() == 0) {
            throw new GenericException("CategoryId is mandatory", HttpStatus.BAD_REQUEST);
        } else {
            categoryService.findById(categoryMetadataFieldValuesDto.getCategoryId());
        }
        if (categoryMetadataFieldValuesDto.getValuesList().isEmpty()) {
            throw new GenericException("Value List is mandatory", HttpStatus.BAD_REQUEST);
        }
    }
}
