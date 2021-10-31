package com.ttn.bootcamp.domains.Product;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonFilter("Filter")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    /*@OneToMany(mappedBy = "category")
    private List<Product> productList;

    @ManyToOne
    @JoinColumn(name = "parent_category_id", referencedColumnName = "id")
    private ParentCategory parentCategory;

    @OneToMany(mappedBy = "category")
    private List<CategoryMetadataFieldValues> categoryMetadataFieldValuesList;*/
}
