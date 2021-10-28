package com.ttn.bootcamp.dto.User;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;


@JsonFilter("Filter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto extends UserDto {

    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$")
    @NotBlank(message = "Phone Number is mandatory")
    private List<Long> contactList;
}
