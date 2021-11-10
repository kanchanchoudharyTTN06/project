package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.Product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
