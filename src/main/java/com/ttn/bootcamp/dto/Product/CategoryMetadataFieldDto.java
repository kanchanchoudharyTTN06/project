package com.ttn.bootcamp.dto.Product;

import com.ttn.bootcamp.domains.Product.CategoryMetadataFieldValues;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMetadataFieldDto {

    private long id;

    @Column(unique = true)
    private String name;

    private List<CategoryMetadataFieldValues> categoryMetadataFieldValues;

    /*@OneToMany(mappedBy = "categoryMetadataField", cascade = CascadeType.ALL)
    private List<CategoryMetadataFieldValues> categoryMetadataFieldValues = new ArrayList<>();*/
}
