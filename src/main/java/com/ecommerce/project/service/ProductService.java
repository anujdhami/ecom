package com.ecommerce.project.service;

import com.ecommerce.project.DTO.ProductDTO;
import com.ecommerce.project.DTO.ResponseDTO;

public interface ProductService {
    ProductDTO createProduct(Long id, ProductDTO productDTO);
    ResponseDTO<ProductDTO> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder);
    ResponseDTO<ProductDTO> getAllProductsByCategory(Long id, int pageNumber, int pageSize, String sortBy, String sortOrder);
    ResponseDTO<ProductDTO> getAllProductsByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortOrder);
    ProductDTO updateProducts(Long id, ProductDTO productDTO);
    ProductDTO deleteProducts(Long id);
}
