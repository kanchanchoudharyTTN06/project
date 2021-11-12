package com.ttn.bootcamp.dto.Product;

import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.domains.Product.CategoryMetadataField;
import com.ttn.bootcamp.domains.Product.CategoryMetadataFieldKey;
import com.ttn.bootcamp.domains.Product.CategoryMetadataFieldValues;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryMetadataFieldValuesDto implements Serializable {
    /*private Category category;
    private CategoryMetadataField categoryMetadataField;*/

    //Doubt in Key
    private CategoryMetadataFieldKey categoryMetadataFieldKey;
    private String valuesList;

    public CategoryMetadataFieldValues toCategoryMetadataFieldValuesEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, CategoryMetadataFieldValues.class);
    }
}
