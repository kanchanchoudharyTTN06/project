package com.ttn.bootcamp.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private long id;
    private String authority;
    private List<UserDto> userList = new ArrayList<>();
}
