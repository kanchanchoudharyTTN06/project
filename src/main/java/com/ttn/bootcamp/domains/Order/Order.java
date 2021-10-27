package com.ttn.bootcamp.domains.Order;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.ttn.bootcamp.domains.User.Address;
import com.ttn.bootcamp.domains.User.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonFilter("OrderFilter")
@AllArgsConstructor
@Table(name = "Order_Details")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int amountPaid;
    private Date dateCreated;
    private String paymentMethod = "COD";

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    private List<Address> customerAddressList;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList = new ArrayList<>();

}
