package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
