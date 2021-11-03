package com.ttn.bootcamp.security;

import com.ttn.bootcamp.domains.User.Role;
import com.ttn.bootcamp.domains.User.User;
import com.ttn.bootcamp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDao {
    private UserRepository userRepository;

    @Autowired
    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    AppUser loadUserByUsername(String email)
    {
        Optional<User> user = userRepository.findByEmail(email); //because emails are unique
        if(user.isPresent())
        {
            List<GrantAuthorityImpl> grantAuthorityList = new ArrayList<>();
            List<Role> roles = user.get().getRoleList();
            for(Role role : roles)
            {
                grantAuthorityList.add(new GrantAuthorityImpl(role.getAuthority()));
            }
            return  new AppUser(user.get().getEmail(), user.get().getPassword(), user.get().isActive(), user.get().isLocked(), grantAuthorityList);
        }
        else
        {
            throw new RuntimeException();
        }
    }
}
