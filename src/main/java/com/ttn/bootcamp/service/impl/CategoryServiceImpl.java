package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.dto.Product.CategoryDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.CategoryRepository;
import com.ttn.bootcamp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    private Category getParentCategory(long id) throws GenericException {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            throw new GenericException("Parent category doesn't exist", HttpStatus.BAD_REQUEST);
        }
        return category.get();
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) throws GenericException {
        checkForCategoryExist(categoryDto.getName());

        Category category = categoryDto.toCategoryEntity();

        if (category.getParentCategory().getId() != 0) {
            Category parent = getParentCategory(category.getParentCategory().getId());
            category.setParentCategory(parent);
        }

        //category.setChildCategories(Collections.singletonList(category));
        categoryDto = categoryRepository.save(category).toCategoryDto();
        return categoryDto;
    }

    @Override
    public List<Category> findAllCategory() throws GenericException {
        List<Category> category = categoryRepository.findAll();
        if (category.isEmpty())
            throw new GenericException("No content found", HttpStatus.NOT_FOUND);
        return category;
    }
}
