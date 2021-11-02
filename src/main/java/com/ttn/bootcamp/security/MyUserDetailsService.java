package com.ttn.bootcamp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository1 userRepository1;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLogin userLogin = userRepository1.findByUsername(username);
        if(userLogin==null)
            throw new UsernameNotFoundException("User : 404");
        return new UserPrincipal(userLogin);
    }
}
