package com.ttn.bootcamp.domains.Product;

import com.ttn.bootcamp.dto.Product.CategoryMetadataFieldValuesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryMetadataFieldValues implements Serializable {

    /*@EmbeddedId
    private CategoryMetadataFieldKey categoryMetadataFieldKey;
    private String name;

    @ManyToOne
    @MapsId("categoryMetadataFieldId")
    @JoinColumn(name = "category_metadata_field_id", referencedColumnName = "id")
    private CategoryMetadataField categoryMetadataField;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;*/

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_metadata_field_id")
    private CategoryMetadataField categoryMetadataField;

    private String valuesList;

    public CategoryMetadataFieldValuesDto toCategoryMetadataFieldValuesDto() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, CategoryMetadataFieldValuesDto.class);
    }
}
