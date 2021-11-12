package com.ttn.bootcamp.domains.Product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.dto.Product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String brand;
    private boolean Is_Cancellable;
    private boolean Is_Returnable;
    private boolean Is_Active = false;
    private boolean Is_Deleted = false;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;

    /*@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductReview> productReviewList;*/

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductVariation> productVariationList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    public ProductDto toProductDto() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, ProductDto.class);
    }

}
