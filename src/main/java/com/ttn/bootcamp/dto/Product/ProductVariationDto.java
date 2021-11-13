package com.ttn.bootcamp.dto.Product;

import com.ttn.bootcamp.domains.Product.ProductVariation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationDto {
    private int id;
    @NotBlank(message = "Quantity is mandatory")
    private int quantityAvailable;
    @NotBlank(message = "Price is mandatory")
    private int price;
    private String image;
    private boolean isActive = true;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String metadata;

    @NotBlank(message = "Product Id is mandatory")
    private long productId;

    public ProductVariation toProductVariationEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, ProductVariation.class);
    }

}
