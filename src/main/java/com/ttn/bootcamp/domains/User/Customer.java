package com.ttn.bootcamp.domains.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {
    @ElementCollection
    @CollectionTable(name = "customer_contacts", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"))
    private List<Long> contactList;

    /*@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ProductReview> productReviewList;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orderList;

    @OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL)
    private List<Cart> carts;*/
}
