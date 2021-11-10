package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.Product.CategoryMetadataField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMetadataFieldRepository extends JpaRepository<CategoryMetadataField, Long> {
}
