package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
