package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.User.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
