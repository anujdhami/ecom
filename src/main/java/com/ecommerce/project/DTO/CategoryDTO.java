package com.ecommerce.project.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    @NotBlank(message = "Name cannot be null")
    @Min(value = 2, message = "Name should  have at least 2 characters")
    private String name;
    private Long id;
}
