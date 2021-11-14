package com.ttn.bootcamp.dto.Product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class MetadataDto {
    @Min(value = 1, message = "Metadata field id is mandatory")
    private Integer id; //metadataFieldId
    @NotBlank(message = "Metadata field value is mandatory")
    private String value; //metadataFieldValue
}
