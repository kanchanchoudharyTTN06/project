package com.ttn.bootcamp.dto.Product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ttn.bootcamp.domains.Product.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private long id;
    @NotBlank(message = "Category name is mandatory")
    private String name;

    @JsonIgnore
    private List<CategoryDto> childCategories;
    private CategoryDto parentCategory;
    private List<ProductDto> productList;

    //private List<CategoryMetadataFieldValuesDto> categoryMetadataFieldValuesList;

    public Category toCategoryEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, Category.class);
    }
}
