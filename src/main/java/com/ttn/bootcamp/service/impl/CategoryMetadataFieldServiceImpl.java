package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.Product.CategoryMetadataField;
import com.ttn.bootcamp.domains.User.Customer;
import com.ttn.bootcamp.dto.Product.CategoryMetadataFieldDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.CategoryMetadataFieldRepository;
import com.ttn.bootcamp.service.CategoryMetadataFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryMetadataFieldServiceImpl implements CategoryMetadataFieldService {
    private CategoryMetadataFieldRepository categoryMetadataFieldRepository;
    private CategoryMetadataFieldService categoryMetadataFieldService;

    @Autowired
    public CategoryMetadataFieldServiceImpl(CategoryMetadataFieldRepository categoryMetadataFieldRepository, CategoryMetadataFieldService categoryMetadataFieldService) {
        this.categoryMetadataFieldRepository = categoryMetadataFieldRepository;
        this.categoryMetadataFieldService = categoryMetadataFieldService;
    }

    @Override
    public void checkForMetadataFieldExist(String name) throws GenericException {
        if (categoryMetadataFieldRepository.findByName(name).isPresent())
            throw new GenericException("Metadata Field already exist.", HttpStatus.BAD_REQUEST);
    }

    @Override
    public CategoryMetadataFieldDto addCategoryMetadataField(CategoryMetadataFieldDto categoryMetadataFieldDto) throws GenericException {
        categoryMetadataFieldService.checkForMetadataFieldExist(categoryMetadataFieldDto.getName());

        CategoryMetadataField categoryMetadataField = categoryMetadataFieldDto.toCategoryMetadataFieldEntity();
        categoryMetadataFieldDto = categoryMetadataFieldRepository.save(categoryMetadataField).toCategoryMetadataFieldDto();
        return categoryMetadataFieldDto;
    }

    public List<CategoryMetadataField> findAllCategoryMetadataFields() throws GenericException {
        List<CategoryMetadataField> categoryMetadataFields = categoryMetadataFieldRepository.findAll();
        if (categoryMetadataFields.isEmpty())
            throw new GenericException("No content found", HttpStatus.NOT_FOUND);
        return categoryMetadataFields;
    }

}
