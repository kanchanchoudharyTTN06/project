package com.ttn.bootcamp.security;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Data
@Entity
public class UserLogin {
        @Id
        private long id;
        @Email
        private String username;
        private String password;

}
