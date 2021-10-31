package com.ttn.bootcamp.dto.User;

import com.ttn.bootcamp.domains.User.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
//@NoArgsConstructor
public class AdminDto extends UserDto {

    public Admin toAdminEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, Admin.class);
    }
}
