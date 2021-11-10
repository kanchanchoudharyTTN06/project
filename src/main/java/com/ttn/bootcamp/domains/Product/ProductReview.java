package com.ttn.bootcamp.domains.Product;

import com.ttn.bootcamp.domains.User.Customer;
import com.ttn.bootcamp.dto.Product.ProductReviewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String review;
    private int rating;

    /*@ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;*/

    public ProductReviewDto toProductReviewDto() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, ProductReviewDto.class);
    }
}
