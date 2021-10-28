package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.User.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
