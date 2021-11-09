package com.ttn.bootcamp.domains.Product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Category> childCategories;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> productList;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<CategoryMetadataFieldValues> categoryMetadataFieldValuesList;


   /* @ManyToOne
    @JoinColumn(name = "parent_category_id", referencedColumnName = "id")
    private Category parentCategory;

    @OneToOne(mappedBy = "category", cascade = CascadeType.ALL)
    Product product;*/
}
