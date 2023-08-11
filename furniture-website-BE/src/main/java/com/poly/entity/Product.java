package com.poly.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "products")
@NamedEntityGraph(name = "graph.product", attributeNodes = {
        @NamedAttributeNode("category")
})
public class Product extends BaseEntity{
    @NotBlank
    private String name;
    private String image;
    private Integer stock;
    @Positive
    private Integer price;
    private Boolean isAvailable = true;
    @Column(name = "cloudinary_id")
    private String cloudinaryId;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
}
