package com.ttn.bootcamp.dto.Product;

import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.domains.Product.CategoryMetadataField;
import com.ttn.bootcamp.domains.Product.Product;
import com.ttn.bootcamp.domains.Product.ProductVariation;
import com.ttn.bootcamp.domains.User.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private long id;
    private String name;
    private String description;
    private String brand;
    private boolean Is_Cancellable;
    private boolean Is_Returnable;
    private boolean Is_Active = false;
    private boolean Is_Deleted = false;

    private Seller seller;

    private List<ProductVariation> productVariationList;

    private Category category;

    public Product toProductEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, Product.class);
    }

}
