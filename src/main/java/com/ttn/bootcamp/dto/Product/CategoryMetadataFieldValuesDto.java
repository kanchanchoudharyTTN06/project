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
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryMetadataFieldValuesDto implements Serializable {
    /*private CategoryDto category;
    private CategoryMetadataFieldDto categoryMetadataField;*/

    private long categoryMetadataFieldId;
    private long categoryId;
    private Set<String> valuesList;
}
