package com.ecommerce.project.service;

import com.ecommerce.project.DTO.CategoryDTO;
import com.ecommerce.project.DTO.ResponseDTO;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO category);
    ResponseDTO<CategoryDTO> getCategory(int pageNumber, int pageSize, String sortBy, String sortOrder);
    CategoryDTO deleteCategory(long id);
    CategoryDTO updateCategory(long id, CategoryDTO category);
}
