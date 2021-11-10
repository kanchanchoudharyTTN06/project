package com.ttn.bootcamp.dto.Product;

import com.ttn.bootcamp.domains.Product.CategoryMetadataField;
import com.ttn.bootcamp.domains.Product.ProductReview;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewDto {
    private int id;
    private String review;
    private int rating;

    /*@ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;*/

    public ProductReview toProductReviewEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, ProductReview.class);
    }
}
