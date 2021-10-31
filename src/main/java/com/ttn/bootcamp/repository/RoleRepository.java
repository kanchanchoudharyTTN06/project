package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByAuthority(String authority);
}
