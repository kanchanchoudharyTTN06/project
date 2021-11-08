package com.ttn.bootcamp.dto.Product;

import com.ttn.bootcamp.domains.Product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariationDto {
    private int id;
    private int quantityAvailable;
    private int price;
    private String image;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String metadata;

    private Product product;

}
