package com.ttn.bootcamp.dto.User;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@JsonFilter("Filter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDto extends UserDto {
    @Pattern(regexp = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$")
    private String gst;
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$")
    @NotBlank(message = "Phone Number is mandatory")
    private List<Long> contactList;
    @NotBlank
    @Size(min = 3, message = ("Company Name should have at least 3 characters"))
    private String companyName;
}
