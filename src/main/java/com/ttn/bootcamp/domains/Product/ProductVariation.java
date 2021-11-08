package com.ttn.bootcamp.domains.Product;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.ttn.bootcamp.domains.Order.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonFilter("Filter")
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

    /*@OneToMany(mappedBy = "productVariation", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();*/

}
