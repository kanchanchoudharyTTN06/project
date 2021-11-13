package com.ttn.bootcamp.domains.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMetadataFieldKey implements Serializable
{
    @Column(name = "CategoryMetadataField_Id", nullable = false)
    private long categoryMetadataFieldId;

    @Column(name = "Category_Id", nullable = false)
    private long categoryId;
}

