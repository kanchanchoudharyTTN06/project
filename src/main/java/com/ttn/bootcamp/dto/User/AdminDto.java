package com.ttn.bootcamp.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
//@NoArgsConstructor
public class AdminDto extends UserDto {
}
