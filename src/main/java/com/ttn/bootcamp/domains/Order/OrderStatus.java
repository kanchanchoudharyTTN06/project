package com.ttn.bootcamp.domains.Order;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFilter("Filter")
public class OrderStatus
{
    @Id
    private int id;

    private String transitionNotesComments;
    private String fromStatus;
    private String toStatus;

    //unidirectional
    /*@OneToOne
    @MapsId
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;*/
}
