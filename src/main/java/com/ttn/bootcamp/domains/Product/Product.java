package com.ttn.bootcamp.domains.Product;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.dto.Product.ProductDto;
import com.ttn.bootcamp.dto.Product.ProductVariationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonFilter("Filter")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String brand;
    private boolean isCancellable = false;
    private boolean isReturnable = false;
    private boolean isActive = false;
    private boolean isDeleted = false;

    //@JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;

    //@JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductVariation> productVariationList;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    /*@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductReview> productReviewList;*/

    public ProductDto toProductDto() {
        List<ProductVariationDto> productVariationDtos = this.productVariationList
                .stream()
                .map(ProductVariation::toProductVariationDto)
                .collect(Collectors.toList());
        return new ProductDto(this.id, this.name, this.description, this.brand, this.isCancellable,
                this.isReturnable, this.isActive, this.isDeleted, this.category.getId(),
                this.seller.getId(), productVariationDtos);
    }
}
