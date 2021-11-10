package com.ttn.bootcamp.dto.Product;

import com.ttn.bootcamp.domains.Product.CategoryMetadataField;
import com.ttn.bootcamp.domains.Product.CategoryMetadataFieldValues;
import com.ttn.bootcamp.domains.User.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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

    public CategoryMetadataField toCategoryMetadataFieldEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, CategoryMetadataField.class);
    }
}
