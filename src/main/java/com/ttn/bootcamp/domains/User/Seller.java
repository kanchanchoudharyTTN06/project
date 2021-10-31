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
@PrimaryKeyJoinColumn
public class Seller extends User {
    private String gst;
    /*@ElementCollection
    @CollectionTable(name = "seller_contacts", joinColumns = @JoinColumn(name = "seller_id", referencedColumnName = "id"))*/
    private long contact;
    private String companyName;
    /*@OneToMany(mappedBy = "seller")
    private List<Product> productList;*/
}
