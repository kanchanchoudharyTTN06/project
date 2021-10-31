package com.ttn.bootcamp.domains.User;

import com.ttn.bootcamp.dto.User.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn
public class Customer extends User {
    //@ElementCollection
    //@CollectionTable(name = "customer_contacts", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"))
    private long contact;

    public CustomerDto toCustomerDto() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, CustomerDto.class);
    }

    /*@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ProductReview> productReviewList;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orderList;

    @OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL)
    private List<Cart> carts;*/
}
