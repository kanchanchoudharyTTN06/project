package com.ttn.bootcamp.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private UserDto user;
}
