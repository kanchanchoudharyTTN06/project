package com.ttn.bootcamp.dto.User;

import com.ttn.bootcamp.domains.User.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto extends UserDto {

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid contact number")
    private String contact;

    public Customer toCustomerEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, Customer.class);
    }
}
