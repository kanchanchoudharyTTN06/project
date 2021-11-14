package com.ttn.bootcamp.dto.Product;

import com.ttn.bootcamp.domains.Product.ProductVariation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationDto {
    private int id;
    @Min(value = 0, message = "Quantity can't be less than 0")
    private int quantityAvailable;
    @Min(value = 0, message = "Price can't be less than 0")
    private int price;
    @NotBlank(message = "Image is mandatory")
    private String image;
    private boolean isActive = true;

    private MetadataDto metadata;

    @Min(value = 1, message = "Product id can't be blank")
    private long productId;

    public ProductVariation toProductVariationEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, ProductVariation.class);
    }

}
