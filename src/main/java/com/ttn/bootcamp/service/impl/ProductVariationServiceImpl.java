package com.ttn.bootcamp.service.impl;

import com.google.gson.Gson;
import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.domains.Product.CategoryMetadataFieldValues;
import com.ttn.bootcamp.domains.Product.Product;
import com.ttn.bootcamp.domains.Product.ProductVariation;
import com.ttn.bootcamp.dto.Product.MetadataDto;
import com.ttn.bootcamp.dto.Product.ProductVariationDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.ProductRepository;
import com.ttn.bootcamp.repository.ProductVariationRepository;
import com.ttn.bootcamp.service.ProductVariationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductVariationServiceImpl implements ProductVariationService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    ProductVariationRepository productVariationRepository;

    @Override
    public ProductVariationDto addProductVariation(ProductVariationDto productVariationDto) throws GenericException {
        Optional<Product> product = productRepository.findById(productVariationDto.getProductId());
        if (!product.isPresent() || product.get().isDeleted())
            throw new GenericException("No Product found for given id", HttpStatus.NOT_FOUND);
        else if (!product.get().isActive())
            throw new GenericException("Product is not active", HttpStatus.INTERNAL_SERVER_ERROR);

        Category category = product.get().getCategory();
        List<MetadataDto> metadataFieldValues = productVariationDto.getMetadataList();
        validate(category, metadataFieldValues);
        validateImage(productVariationDto.getImage());
        ProductVariation productVariation = productVariationDto.toProductVariationEntity();
        productVariation.setProduct(product.get());
        return productVariationRepository.save(productVariation).toProductVariationDto();
    }

    private void validateImage(String image) throws GenericException {
        String[] strings = image.split(",");
        List<String> validImageList = Arrays.asList("data:image/jpeg;base64", "data:image/png;base64", "data:image/jpg;base64");
        if (!validImageList.contains(strings[0])) {
            throw new GenericException("Invalid image", HttpStatus.BAD_REQUEST);
        }
    }

    private void validate(Category category, List<MetadataDto> metadataFieldValues) throws GenericException {
        long categoryId = category.getId();
        List<Long> categoryMetadataFieldIds = category.getCategoryMetadataFieldValuesList()
                .stream()
                .map(categoryMetadataFieldValues -> categoryMetadataFieldValues.getCategoryMetadataField().getId())
                .collect(Collectors.toList());
        List<CategoryMetadataFieldValues> categoryMetadataFieldValues = category.getCategoryMetadataFieldValuesList();
        for (MetadataDto metadataDto : metadataFieldValues) {
            long metadataFieldId = metadataDto.getId();
            if (!categoryMetadataFieldIds.contains(metadataFieldId)) {
                throw new GenericException("Category Metadata Field not found", HttpStatus.INTERNAL_SERVER_ERROR); //if category metadatafield is associated with it or not
            }
            CategoryMetadataFieldValues categoryMetadataFieldValues1 = categoryMetadataFieldValues.stream()
                    .filter(categoryMetadataFieldValues2 -> Objects.equals(categoryMetadataFieldValues2.getCategory().getId(), categoryId) && Objects.equals(categoryMetadataFieldValues2.getCategoryMetadataField().getId(), metadataFieldId))
                    .findFirst().get();
            String requestValue = metadataDto.getValue();
            List<String> availableValueList = Arrays.asList(categoryMetadataFieldValues1.getValuesList().split(","));
            List<String> requestedValueList = Arrays.asList(requestValue.split(","));
            for (String value : requestedValueList) {
                if (!availableValueList.contains(value.trim()))
                    throw new GenericException("Metadata Field Value should be within the possible values", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
