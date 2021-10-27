package com.ttn.bootcamp.domains.User;

import com.ttn.bootcamp.domains.Product.Product;
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
public class Seller extends User {
    private String gst;
    @ElementCollection
    @CollectionTable(name = "seller_contacts", joinColumns = @JoinColumn(name = "seller_id", referencedColumnName = "id"))
    private List<Long> contactList;
    private String companyName;
    @OneToMany(mappedBy = "seller")
    private List<Product> productList;
}
