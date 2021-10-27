package com.ttn.bootcamp.domains.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProductVariationKey implements Serializable
{
    private int customerId;
    private int productVariationId;
}
