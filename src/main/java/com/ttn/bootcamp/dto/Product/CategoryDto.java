package com.ttn.bootcamp.dto.Product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.domains.Product.CategoryMetadataFieldValues;
import com.ttn.bootcamp.domains.Product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private long id;
    private String name;

    //@JsonIgnore
    private List<Category> childCategories;

    private Category parentCategory;

    private List<Product> productList;

    private List<CategoryMetadataFieldValues> categoryMetadataFieldValuesList;
}
