package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="categories")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Category {
    @NotBlank(message = "Name cannot be null")
    @Min(value = 2, message = "Name should  have at least 2 characters")
    @Column(unique = true)
    private String name;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
}
