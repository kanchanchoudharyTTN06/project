package com.ttn.bootcamp.service.impl;

import com.ttn.bootcamp.domains.Product.Category;
import com.ttn.bootcamp.domains.Product.CategoryMetadataFieldValues;
import com.ttn.bootcamp.domains.Product.Product;
import com.ttn.bootcamp.domains.Product.ProductVariation;
import com.ttn.bootcamp.dto.Product.MetadataDto;
import com.ttn.bootcamp.dto.Product.ProductVariationDto;
import com.ttn.bootcamp.exceptions.GenericException;
import com.ttn.bootcamp.repository.ProductRepository;
import com.ttn.bootcamp.repository.ProductVariationRepository;
import com.ttn.bootcamp.security.AppUser;
import com.ttn.bootcamp.service.ProductService;
import com.ttn.bootcamp.service.ProductVariationService;
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

    private ProductVariationRepository productVariationRepository;
    private ProductService productService;

    @Autowired
    public ProductVariationServiceImpl(ProductVariationRepository productVariationRepository, ProductService productService) {
        this.productVariationRepository = productVariationRepository;
        this.productService = productService;
    }

    @Override
    public ProductVariationDto addOrUpdateProductVariation(ProductVariationDto productVariationDto) throws GenericException {
        Product product = productService.getProductById(productVariationDto.getProductId());

        Category category = product.getCategory();
        List<MetadataDto> metadataFieldValues = productVariationDto.getMetadataList();
        validate(category, metadataFieldValues);
        validateImage(productVariationDto.getImage());
        ProductVariation productVariation = productVariationDto.toProductVariationEntity();
        productVariation.setProduct(product);
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

    @Override
    public ProductVariationDto getProductVariationById(AppUser principal, long id) throws GenericException {
        Optional<ProductVariation> productVariation = productVariationRepository.findById(id);
        if (productVariation.isPresent()) {
            return productVariation.get().toProductVariationDto();
        }
        throw new GenericException("No product variation found for given id and parent category", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<ProductVariationDto> getProductVariationByProduct(AppUser principal, long productId) throws GenericException {
        Product product = productService.getProductById(productId);
        List<ProductVariation> productVariationList = productVariationRepository.findByProduct(product);
        if (productVariationList.isEmpty())
            throw new GenericException("No Product variation found", HttpStatus.NOT_FOUND);
        return productVariationList.stream().map(ProductVariation::toProductVariationDto)
                .collect(Collectors.toList());
    }
}
