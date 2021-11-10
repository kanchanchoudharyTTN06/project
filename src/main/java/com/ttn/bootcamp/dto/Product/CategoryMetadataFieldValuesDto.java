package com.ttn.bootcamp.dto.Product;

import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.domains.Product.CategoryMetadataField;
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
    /*@ManyToOne
    @MapsId("categoryMetadataFieldId")
    @JoinColumn(name = "category_metadata_field_id", referencedColumnName = "id")
    private CategoryMetadataField categoryMetadataField;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;*/

    private Category category;

    private CategoryMetadataField categoryMetadataField;

    private String valuesList;

    public CategoryMetadataFieldValues toCategoryMetadataFieldValuesEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, CategoryMetadataFieldValues.class);
    }
}
