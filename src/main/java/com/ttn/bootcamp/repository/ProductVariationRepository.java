package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.Product.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {
}
