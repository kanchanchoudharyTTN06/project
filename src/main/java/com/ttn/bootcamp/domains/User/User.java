package com.ttn.bootcamp.domains.User;

import com.ttn.bootcamp.dto.User.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roleList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addressList;

    public UserDto toUserDto() {
        return new UserDto().builder()
                .id(this.id)
                .email(this.email)
                .firstName(this.firstName)
                .middleName(this.middleName)
                .lastName(this.lastName)
                .password(this.password)
                .isDeleted(this.isDeleted)
                .isActive(this.isActive)
                .isLocked(this.isLocked)
                .isExpired(this.isExpired)
                .invalidAttemptCount(this.invalidAttemptCount)
                .addressList(this.addressList)
                .roleList(this.roleList)
                .confirmPassword(this.password)
                .build();
    }
}
