package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.Product.Product;
import com.ttn.bootcamp.domains.Product.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {
    List<ProductVariation> findByProduct(Product product);
}
