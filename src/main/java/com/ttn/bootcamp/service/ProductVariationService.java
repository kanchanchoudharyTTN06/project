package com.ttn.bootcamp.service;

import com.ttn.bootcamp.dto.Product.ProductVariationDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.security.AppUser;

import java.util.List;

public interface ProductVariationService {
    ProductVariationDto addOrUpdateProductVariation(ProductVariationDto productVariationDto) throws GenericException;

    ProductVariationDto getProductVariationById(AppUser principal, long id) throws GenericException;

    List<ProductVariationDto> getProductVariationByProduct(AppUser principal, long productId) throws GenericException;
}
