package com.ttn.bootcamp.domains.Order;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.ttn.bootcamp.domains.Product.ProductVariation;
import com.ttn.bootcamp.domains.User.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFilter("Filter")
public class Cart
{
    @EmbeddedId
    private CustomerProductVariationKey customerProductVariationKey = new CustomerProductVariationKey();
    private int quantity;
    private boolean isWishlistItem;

    /*@ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @MapsId("productVariationId")
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;*/
}
