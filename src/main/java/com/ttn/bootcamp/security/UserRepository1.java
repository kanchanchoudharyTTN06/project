package com.ttn.bootcamp.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository1 extends JpaRepository<UserLogin, Long>{
    UserLogin findByUsername(String username);
}
