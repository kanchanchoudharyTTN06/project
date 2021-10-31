package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.User.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
