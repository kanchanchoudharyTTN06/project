package com.ttn.bootcamp.security;

import com.ttn.bootcamp.domains.User.Role;
import com.ttn.bootcamp.domains.User.User;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.UserRepository;
import com.ttn.bootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.*;

@Repository
@Transactional
public class UserDao {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private UserService userService;

    @Autowired
    public UserDao(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    AppUser loadUserByUsername(String email, String password) throws GenericException {
        Optional<User> user = userRepository.findByEmail(email); //because emails are unique
        if (user.isPresent()) {
            if (!user.get().isActive()) {
                throw new GenericException("Account is not active", HttpStatus.UNAUTHORIZED);
            }
            if (user.get().isLocked()) {
                throw new GenericException("Account is locked", HttpStatus.UNAUTHORIZED);
            }

            List<GrantAuthorityImpl> grantAuthorityList = new ArrayList<>();
            List<Role> roles = user.get().getRoleList();
            for (Role role : roles) {
                grantAuthorityList.add(new GrantAuthorityImpl(role.getAuthority()));
            }
            if (Objects.nonNull(password) && !passwordEncoder.matches(password, user.get().getPassword())) {
                user.get().setInvalidAttemptCount(user.get().getInvalidAttemptCount() + 1);
                if (user.get().getInvalidAttemptCount() == 3) {
                    user.get().setLocked(true);
                    userService.userAccountLockedEmailHandler(user.get());
                }
            } else {
                user.get().setInvalidAttemptCount(0);
                user.get().setLocked(false);
            }
            userRepository.save(user.get());

            return new AppUser(user.get().getEmail(), user.get().getPassword(), user.get().isActive(), user.get().isLocked(), grantAuthorityList);
        } else {
            throw new GenericException("username is incorrect.", HttpStatus.UNAUTHORIZED);
        }
    }
}
