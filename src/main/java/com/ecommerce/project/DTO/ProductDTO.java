package com.ecommerce.project.DTO;

import com.ecommerce.project.model.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
public class ProductDTO {
    private Long id;
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3)
    private String productName;
    @Size(min = 3,max = 580)
    private String description;
    @Min(value = 0)
    private int quantity;
    @Min(value = 1)
    private double price;
    private double specialPrice;
    private double discount;
    Category category;
    private String imageName;
}
