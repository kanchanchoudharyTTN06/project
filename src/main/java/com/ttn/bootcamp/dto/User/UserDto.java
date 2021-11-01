package com.ttn.bootcamp.dto.User;

import com.ttn.bootcamp.domains.User.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",
            message = "Password must contains uppercase, special character and digit of minimum length 8")
    private String password;
    @NotBlank(message = "Confirm Password is mandatory")
    private String confirmPassword;
    private boolean isDeleted = false;
    private boolean isActive = false;
    private boolean isLocked = false;
    private boolean isExpired = false;
    private int invalidAttemptCount;
    private Date lockTime;
    private List<RoleDto> roleList = new ArrayList<>();
    private List<AddressDto> addressList;

    public User toUserEntity() {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(this, User.class);
    }
}
