package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.domains.Product.CategoryMetadataFieldKey;
import com.ttn.bootcamp.domains.Product.CategoryMetadataFieldValues;
import com.ttn.bootcamp.dto.Product.CategoryDto;
import com.ttn.bootcamp.dto.Product.CategoryMetadataFieldValuesDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.CategoryRepository;
import com.ttn.bootcamp.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private boolean checkForCategoryExist(String name) throws GenericException {
        return categoryRepository.findByName(name).isPresent();
    }

    private Category getParentCategory(long id) throws GenericException {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            throw new GenericException("Parent category doesn't exist", HttpStatus.BAD_REQUEST);
        }
        return category.get();
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) throws GenericException {
        if (checkForCategoryExist(categoryDto.getName()))
            throw new GenericException("Category already exist.", HttpStatus.BAD_REQUEST);

        Category category = categoryDto.toCategoryEntity();

        if (category.getParentCategory().getId() != 0) {
            Category parent = getParentCategory(category.getParentCategory().getId());
            category.setParentCategory(parent);
        } else if (StringUtils.isBlank(categoryDto.getParentCategory().getName())) {
            throw new GenericException("Parent category name and id both can't be blank", HttpStatus.BAD_REQUEST);
        } else if (checkForCategoryExist(categoryDto.getParentCategory().getName())) {
            throw new GenericException("Parent category already exist.", HttpStatus.BAD_REQUEST);
        }
        return categoryRepository.save(category).toCategoryDto();
    }

    @Override
    public List<Category> findAllCategory() throws GenericException {
        List<Category> category = categoryRepository.findAll();
        if (category.isEmpty())
            throw new GenericException("No content found", HttpStatus.NOT_FOUND);
        return category;
    }

    @Override
    public Category findById(long id) throws GenericException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent())
            return categoryOptional.get();
        throw new GenericException("No Category found.", HttpStatus.NOT_FOUND);
    }

    @Override
    public Category updateCategory(CategoryDto categoryDto) throws GenericException {
        if (categoryDto.getId() == 0)
            throw new GenericException("Category id is mandatory", HttpStatus.INTERNAL_SERVER_ERROR);
        Category category = categoryDto.toCategoryEntity();
        if (Objects.nonNull(category.getParentCategory()) && category.getParentCategory().getId() != 0) {
            Category parent = getParentCategory(category.getParentCategory().getId());
            category.setParentCategory(parent);
        } else if (StringUtils.isNotBlank(category.getParentCategory().getName())) {
            Category parent = categoryRepository.save(category.getParentCategory());
            category.setParentCategory(parent);
        }
        return categoryRepository.save(category);
    }
}
