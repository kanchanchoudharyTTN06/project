package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.domains.Product.CategoryMetadataField;
import com.ttn.bootcamp.domains.User.Customer;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.dto.Product.CategoryDto;
import com.ttn.bootcamp.dto.Product.CategoryMetadataFieldDto;
import com.ttn.bootcamp.dto.Product.CategoryMetadataFieldValuesDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private CustomerService customerService;
    private SellerService sellerService;
    private UserService userService;
    private CategoryMetadataFieldService categoryMetadataFieldService;
    private CategoryService categoryService;
    private CategoryMetadataFieldValueService categoryMetadataFieldValueService;

    @Autowired
    public AdminServiceImpl(CustomerService customerService, SellerService sellerService, UserService userService, CategoryMetadataFieldService categoryMetadataFieldService, CategoryService categoryService, CategoryMetadataFieldValueService categoryMetadataFieldValueService) {
        this.customerService = customerService;
        this.sellerService = sellerService;
        this.userService = userService;
        this.categoryMetadataFieldService = categoryMetadataFieldService;
        this.categoryService = categoryService;
        this.categoryMetadataFieldValueService = categoryMetadataFieldValueService;
    }

    @Override
    public List<Customer> getAllCustomers() throws GenericException {
        return customerService.findAllCustomers();
    }

    @Override
    public List<Seller> getAllSellers() throws GenericException {
        return sellerService.findAllSellers();
    }

    @Override
    public String activateUser(Map<String, String> request) throws GenericException {
        Set<Map.Entry<String, String>> entry = request.entrySet();
        long id = Long.parseLong(entry.stream().iterator().next().getValue());
        return userService.activateUserAccountByAdmin(id);
    }

    @Override
    public String deActivateUser(Map<String, String> request) throws GenericException {
        Set<Map.Entry<String, String>> entry = request.entrySet();
        long id = Long.parseLong(entry.stream().iterator().next().getValue());
        return userService.deActivateUserAccountByAdmin(id);
    }

    @Override
    public List<CategoryMetadataField> getAllCategoryMetadataFields() throws GenericException {
        return categoryMetadataFieldService.findAllCategoryMetadataFields();
    }

    @Override
    public List<CategoryDto> getAllCategory() throws GenericException {
        List<Category> categories = categoryService.findAllCategory();
        if (Objects.nonNull(categories) && !categories.isEmpty()) {
            return categories.stream().map(Category::toCategoryDto)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) throws GenericException {
        return categoryService.updateCategory(categoryDto).toCategoryDto();
    }

    @Override
    public CategoryDto getCategory(long id) throws GenericException {
        return categoryService.findById(id).toCategoryDto();
    }

    @Override
    public CategoryMetadataFieldValuesDto addOrUpdateCategoryMetadataFieldValues(CategoryMetadataFieldValuesDto categoryMetadataFieldValuesDto) throws GenericException {
        return categoryMetadataFieldValueService.addOrUpdateCategoryMetadataFieldValues(categoryMetadataFieldValuesDto);
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) throws GenericException {
        return categoryService.addCategory(categoryDto);
    }

    @Override
    public CategoryMetadataFieldDto addCategoryMetadataField(CategoryMetadataFieldDto categoryMetadataFieldDto) throws GenericException {
        return categoryMetadataFieldService.addCategoryMetadataField(categoryMetadataFieldDto);
    }
}
