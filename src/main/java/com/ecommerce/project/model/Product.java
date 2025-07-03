package com.ecommerce.project.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String productName;
    private String description;
    private int quantity;
    private double price;
    private double specialPrice;
    private double discount;
    private String imageName;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
}
