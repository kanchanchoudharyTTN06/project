package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.Product.CategoryMetadataField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryMetadataFieldRepository extends JpaRepository<CategoryMetadataField, Long> {
    Optional<CategoryMetadataField> findByName(String name);
}
