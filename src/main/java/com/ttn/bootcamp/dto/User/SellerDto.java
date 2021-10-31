package com.ttn.bootcamp.dto.User;

import com.ttn.bootcamp.domains.User.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDto extends UserDto {
    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$")
    private String gst;
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$")
    private String contact;
    @NotBlank
    @Size(min = 3, message = ("Company Name should have at least 3 characters"))
    private String companyName;

    public Seller toSellerEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, Seller.class);
    }
}
