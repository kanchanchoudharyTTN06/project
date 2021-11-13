package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.Product.Product;
import com.ttn.bootcamp.domains.User.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
