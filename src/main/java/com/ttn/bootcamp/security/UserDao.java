package com.ttn.bootcamp.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    List<User> userList = Arrays.asList(
            new User("user",
                    passwordEncoder.encode("pass"),
                    Arrays.asList(new GrantAuthorityImpl("ROLE_USER"))),
            new User("admin",
                    passwordEncoder.encode("pass"),
                    Arrays.asList(new GrantAuthorityImpl("ROLE_ADMIN"))));

   public User loadUserByUsername(String username) {
        Optional<User> userOptional = userList.stream()
                .filter(e -> e.getUsername().equals(username)).findFirst();

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("User not found");
        }

    }

}
