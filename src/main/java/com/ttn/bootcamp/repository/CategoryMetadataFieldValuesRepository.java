package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.Product.CategoryMetadataFieldValues;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMetadataFieldValuesRepository extends JpaRepository<CategoryMetadataFieldValues, Long> {
}
