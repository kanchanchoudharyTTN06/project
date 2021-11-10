package com.ttn.bootcamp.domains.Product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ttn.bootcamp.dto.Product.CategoryMetadataFieldDto;
import com.ttn.bootcamp.dto.User.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMetadataField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "categoryMetadataField", cascade = CascadeType.ALL)
    private List<CategoryMetadataFieldValues> categoryMetadataFieldValues;

    public CategoryMetadataFieldDto toCategoryMetadataFieldDto() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, CategoryMetadataFieldDto.class);
    }

    /*@JsonIgnore
    @OneToMany(mappedBy = "categoryMetadataField", cascade = CascadeType.ALL)
    private List<CategoryMetadataFieldValues> categoryMetadataFieldValues = new ArrayList<>();*/
}
