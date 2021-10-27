package com.ttn.bootcamp.domains.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserImpl implements UserDetails {
    private String username;
    private String password;

    List<GrantAuthorityImpl> grantAuthorityList;

    public UserImpl() {

    }

    public UserImpl(String username, String password, List<GrantAuthorityImpl> grantAuthorityList) {
        this.username = username;
        this.password = password;
        this.grantAuthorityList = grantAuthorityList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantAuthorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
