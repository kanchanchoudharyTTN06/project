package com.ttn.bootcamp.dao;

import com.ttn.bootcamp.domains.User.GrantAuthorityImpl;
import com.ttn.bootcamp.domains.User.UserImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    List<UserImpl> userList = Arrays.asList(
            new UserImpl("user",
                    passwordEncoder.encode("pass"),
                    Arrays.asList(new GrantAuthorityImpl("ROLE_USER"))),
            new UserImpl("admin",
                    passwordEncoder.encode("pass"),
                    Arrays.asList(new GrantAuthorityImpl("ROLE_ADMIN"))));

   public UserImpl loadUserByUsername(String username) {
        Optional<UserImpl> userOptional = userList.stream()
                .filter(e -> e.getUsername().equals(username)).findFirst();

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("User not found");
        }

    }

}
