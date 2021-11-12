package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.dto.Product.CategoryDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.CategoryRepository;
import com.ttn.bootcamp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private void checkForCategoryExist(String name) throws GenericException {
        if (categoryRepository.findByName(name).isPresent())
            throw new GenericException("Category already exist.", HttpStatus.BAD_REQUEST);
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) throws GenericException {
        checkForCategoryExist(categoryDto.getName());

        Category category = categoryDto.toCategoryEntity();
        categoryDto = categoryRepository.save(category).toCategoryDto();
        return categoryDto;
    }

    public List<Category> findAllCategory() throws GenericException {
        List<Category> category = categoryRepository.findAll();
        if (category.isEmpty())
            throw new GenericException("No content found", HttpStatus.NOT_FOUND);
        return category;
    }
}
