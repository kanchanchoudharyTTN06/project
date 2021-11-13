package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.domains.Product.Product;
import com.ttn.bootcamp.domains.User.Seller;
import com.ttn.bootcamp.dto.Product.ProductDto;
import com.ttn.bootcamp.dto.User.SellerDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.CategoryRepository;
import com.ttn.bootcamp.repository.ProductRepository;
import com.ttn.bootcamp.security.AppUser;
import com.ttn.bootcamp.service.ProductService;
import com.ttn.bootcamp.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private SellerService sellerService;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, SellerService sellerService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.sellerService = sellerService;
        this.categoryRepository = categoryRepository;
    }

    private void checkForProductExist(String name, String brand, Category category, Seller seller) throws GenericException {

        if (productRepository.findByNameAndBrandAndCategoryAndSeller(name, brand, category, seller).isPresent())
            throw new GenericException("Product already exist.", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ProductDto addProduct(ProductDto productDto, AppUser appUser) throws GenericException {
        Seller seller = sellerService.getSellerProfile(appUser).toSellerEntity();
        Optional<Category> category = categoryRepository.findById(productDto.getCategoryId());
        if (!category.isPresent())
            throw new GenericException("Category is not present.", HttpStatus.NOT_FOUND);
        checkForProductExist(productDto.getName(), productDto.getBrand(), category.get(), seller);

        Product product = productDto.toProductEntity();
        product.setCategory(category.get());
        product.setSeller(seller);
        productDto = productRepository.save(product).toProductDto();
        productDto.setCategoryId(product.getCategory().getId());
        return productDto;
    }

    @Override
    public List<ProductDto> getAllProducts(AppUser appUser) throws GenericException {
        SellerDto sellerDto = sellerService.getSellerProfile(appUser);
        Optional<List<Product>> productList = productRepository.findBySeller(sellerDto.toSellerEntity());
        if (productList.isPresent())
            return productList.get().stream().map(Product::toProductDto)
                    .collect(Collectors.toList());
        throw new GenericException("No content found", HttpStatus.NOT_FOUND);
    }
}
