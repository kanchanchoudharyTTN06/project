package com.ttn.bootcamp.domains.User;

import com.ttn.bootcamp.domains.Product.Product;
import com.ttn.bootcamp.dto.User.SellerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
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

    public SellerDto toSellerDto() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, SellerDto.class);
    }
    @OneToMany(mappedBy = "seller")
    private List<Product> productList;
}
