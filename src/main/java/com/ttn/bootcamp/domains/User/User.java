package com.ttn.bootcamp.domains.User;

import com.ttn.bootcamp.dto.User.AddressDto;
import com.ttn.bootcamp.dto.User.RoleDto;
import com.ttn.bootcamp.dto.User.UserDto;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
//@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private boolean isDeleted = false;
    private boolean isActive = false;
    private boolean isLocked = false;
    private boolean isExpired = false;
    private int invalidAttemptCount;
    private Date lockTime;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roleList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addressList;

    public UserDto toUserDto() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, UserDto.class);
    }
}
