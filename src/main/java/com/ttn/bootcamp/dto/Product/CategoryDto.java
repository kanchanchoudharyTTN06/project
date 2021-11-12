package com.ttn.bootcamp.dto.Product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.domains.Product.CategoryMetadataFieldValues;
import com.ttn.bootcamp.domains.Product.Product;
import com.ttn.bootcamp.domains.User.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private long id;
    private String name;

    /*@JsonIgnore
    private List<Category> childCategories;*/

    private CategoryDto parentCategory;

    private List<ProductDto> productList;

    //private List<CategoryMetadataFieldValuesDto> categoryMetadataFieldValuesList;

    public Category toCategoryEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, Category.class);
    }
}
