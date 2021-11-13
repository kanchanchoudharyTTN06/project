package com.ttn.bootcamp.repository;

import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.domains.Product.Product;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.dto.Product.ProductDto;
import com.ttn.bootcamp.exceptions.GenericException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<ProductDto> findByNameAndBrandAndCategoryAndSeller(String name, String brand, Category category, Seller seller);

    Optional<List<Product>> findBySeller(Seller seller);
}
