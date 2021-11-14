package com.ttn.bootcamp.dto.Product;

import com.google.gson.Gson;
import com.ttn.bootcamp.domains.Product.ProductVariation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationDto {
    private long id;
    @Min(value = 0, message = "Quantity can't be less than 0")
    private int quantityAvailable;
    @Min(value = 0, message = "Price can't be less than 0")
    private int price;
    @NotBlank(message = "Image is mandatory")
    private String image;
    private boolean isActive = true;

    private List<MetadataDto> metadataList;

    @Min(value = 1, message = "Product id can't be blank")
    private long productId;

    public ProductVariation toProductVariationEntity() {
        ModelMapper mapper = new ModelMapper();
        ProductVariation productVariation = mapper.map(this, ProductVariation.class);
        productVariation.setMetadata(new Gson().toJson(this.metadataList));
        return productVariation;
    }

}
