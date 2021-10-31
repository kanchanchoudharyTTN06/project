package com.ttn.bootcamp.domains.User;

import com.ttn.bootcamp.dto.User.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String authority;

    /*@ManyToMany(mappedBy = "roleList")
    private List<User> userList;*/

    public RoleDto toRoleDto() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, RoleDto.class);
    }
}
