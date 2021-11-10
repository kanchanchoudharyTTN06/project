package com.ttn.bootcamp.domains.Product;

import com.ttn.bootcamp.dto.Product.CategoryDto;
import com.ttn.bootcamp.dto.Product.ProductVariationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantityAvailable;
    private int price;
    private String image;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String metadata;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public ProductVariationDto toProductVariationDto() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, ProductVariationDto.class);
    }

    /*@OneToMany(mappedBy = "productVariation", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();*/

}
