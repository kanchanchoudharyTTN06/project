package com.ttn.bootcamp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPassword {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email")
    private String email;
    @NotBlank(message = "Token is mandatory")
    private String token;
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",
            message = "Password must contains uppercase, special character and digit of minimum length 8")
    private String password;
    @NotBlank(message = "Confirm password is mandatory")
    private String confirmPassword;
}
