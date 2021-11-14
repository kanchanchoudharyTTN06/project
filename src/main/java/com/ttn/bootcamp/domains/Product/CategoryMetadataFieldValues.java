package com.ttn.bootcamp.domains.Product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ttn.bootcamp.dto.Product.CategoryMetadataFieldValuesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryMetadataFieldValues implements Serializable {

    @EmbeddedId
    private CategoryMetadataFieldKey categoryMetadataFieldKey;
    private String valuesList;

    @JsonIgnore
    @ManyToOne
    @MapsId("categoryMetadataFieldId")
    @JoinColumn(name = "category_metadata_field_id", referencedColumnName = "id")
    private CategoryMetadataField categoryMetadataField;

    @JsonIgnore
    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    /*@Id
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @Id
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_metadata_field_id")
    private CategoryMetadataField categoryMetadataField;*/

    public CategoryMetadataFieldValuesDto toCategoryMetadataFieldValuesDto() {
        return new CategoryMetadataFieldValuesDto(this.categoryMetadataFieldKey.getCategoryMetadataFieldId(),
                this.getCategoryMetadataFieldKey().getCategoryId(),
                Arrays.stream(this.valuesList.split(",")).collect(Collectors.toSet()));
    }
}
