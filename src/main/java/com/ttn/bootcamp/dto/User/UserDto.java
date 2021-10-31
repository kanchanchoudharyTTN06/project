package com.ttn.bootcamp.dto.User;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.sun.istack.internal.NotNull;
import com.ttn.bootcamp.domains.User.Address;
import com.ttn.bootcamp.domains.User.Role;
import com.ttn.bootcamp.domains.User.User;
import com.ttn.bootcamp.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//@JsonFilter("Filter")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private long id;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "First Name is mandatory")
    @Size(min = 3, message = "First Name should have at least 3 characters")
    private String firstName;
    private String middleName;
    @NotBlank(message = "Last Name is mandatory")
    @Size(min = 3, message = "Last Name should have at least 3 characters")
    private String lastName;
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$")
    private String password;
    @NotBlank(message = "Confirm Password is mandatory")
    private String confirmPassword;
    private boolean isDeleted = false;
    private boolean isActive = false;
    private boolean isLocked = false;
    private boolean isExpired = false;
    private int invalidAttemptCount;
    private Date lockTime;
    private List<Role> roleList = new ArrayList<>();
    private int userType;
    private List<Address> addressList;

    public User toUserEntity() {
        return new User().builder()
                .email(this.email)
                .firstName(this.firstName)
                .middleName(this.middleName)
                .lastName(this.lastName)
                .password(this.password)
                .isDeleted(this.isDeleted)
                .isActive(this.isActive)
                .isLocked(this.isLocked)
                .isExpired(this.isExpired)
                .roleList(this.roleList)
                .invalidAttemptCount(this.invalidAttemptCount)
                .addressList(this.addressList)
                .build();
    }
}
