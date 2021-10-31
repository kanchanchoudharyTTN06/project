package com.ttn.bootcamp.dto.User;

import com.ttn.bootcamp.domains.User.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private long id;
    private String authority;
    private List<UserDto> userList = new ArrayList<>();

    public Role toRoleEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, Role.class);
    }
}
