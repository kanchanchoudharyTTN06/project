package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.Product.Product;
import com.ttn.bootcamp.dto.Product.ProductVariationDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.ProductRepository;
import com.ttn.bootcamp.service.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductVariationServiceImpl implements ProductVariationService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductVariationDto addProductVariation(ProductVariationDto productVariationDto) throws GenericException {
        Optional<Product> product = productRepository.findById(productVariationDto.getProductId());
        if (!product.isPresent())
            throw new GenericException("No Product found of given id", HttpStatus.NOT_FOUND);
        else if (!product.get().isActive())
            throw new GenericException("Product is not active", HttpStatus.INTERNAL_SERVER_ERROR);
        else if (product.get().isDeleted())
            throw new GenericException("Product has deleted", HttpStatus.INTERNAL_SERVER_ERROR);

        validateAddProductVariationRequest(productVariationDto);
        return null;
    }

    private void validateAddProductVariationRequest(ProductVariationDto productVariationDto) throws GenericException {
        validImage(productVariationDto.getImage());
    }

    private void validImage(String image) throws GenericException {
        String[] strings = image.split(",");
        List<String> validImageList = Arrays.asList("data:image/jpeg;base64", "data:image/png;base64", "data:image/jpg;base64");
        if (!validImageList.contains(strings[0])) {
            throw new GenericException("Invalid image", HttpStatus.BAD_REQUEST);
        }
    }
}
