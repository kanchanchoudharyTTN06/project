package com.ttn.bootcamp.dto.User;

import com.ttn.bootcamp.domains.User.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private long id;
    private String city;
    private String state;
    private String country;
    private String addressLine;
    private int zipCode;
    private String label;
    //private UserDto user;

    public Address toAddressEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, Address.class);
    }
}
