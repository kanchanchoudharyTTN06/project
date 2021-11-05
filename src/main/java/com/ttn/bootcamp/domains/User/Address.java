package com.ttn.bootcamp.domains.User;

import com.ttn.bootcamp.dto.User.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String city;
    private String state;
    private String country;
    private String addressLine;
    private int zipCode;
    private String label;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public AddressDto toAddressDto() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, AddressDto.class);
    }
}
