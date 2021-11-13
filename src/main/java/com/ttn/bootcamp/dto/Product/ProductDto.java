package com.ttn.bootcamp.dto.Product;

import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.domains.Product.CategoryMetadataField;
import com.ttn.bootcamp.domains.Product.Product;
import com.ttn.bootcamp.domains.Product.ProductVariation;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.dto.User.SellerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String description;
    @NotBlank(message = "Brand Name is mandatory")
    private String brand;
    private boolean isCancellable = false;
    private boolean isReturnable = false;
    private boolean isActive = false;
    private boolean isDeleted = false;
    private long categoryId;

    private SellerDto seller;

    private List<ProductVariationDto> productVariationList;

    public Product toProductEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, Product.class);
    }

}
