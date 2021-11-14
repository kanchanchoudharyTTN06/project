package com.ttn.bootcamp.service;

import com.ttn.bootcamp.dto.Product.ProductVariationDto;
import com.ttn.bootcamp.exceptions.GenericException;

public interface ProductVariationService {
    ProductVariationDto addProductVariation(ProductVariationDto productVariationDto) throws GenericException;
}
