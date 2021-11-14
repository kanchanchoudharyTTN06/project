package com.ttn.bootcamp.domains.Product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttn.bootcamp.dto.Product.CategoryDto;
import com.ttn.bootcamp.dto.Product.MetadataDto;
import com.ttn.bootcamp.dto.Product.ProductVariationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.type.TypeFactory;
import org.hibernate.annotations.Type;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int quantityAvailable;
    private int price;
    private String image;
    private boolean isActive = true;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String metadata;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public ProductVariationDto toProductVariationDto() {
        ModelMapper mapper = new ModelMapper();
        ProductVariationDto productVariationDto = mapper.map(this, ProductVariationDto.class);
        productVariationDto.setProductId(this.getProduct().getId());
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<MetadataDto> metadataList = new ArrayList<>();
        try {
            metadataList = objectMapper.readValue(this.metadata, ArrayList.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        productVariationDto.setMetadataList(metadataList);
        return productVariationDto;
    }

    /*@OneToMany(mappedBy = "productVariation", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();*/

}
