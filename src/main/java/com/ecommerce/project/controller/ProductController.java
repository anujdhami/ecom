package com.ecommerce.project.controller;

import com.ecommerce.project.DTO.ProductDTO;
import com.ecommerce.project.DTO.ResponseDTO;
import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ecommerce.project.config.AppConstants.SORT_BY;
import static com.ecommerce.project.config.AppConstants.SORT_ORDER;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> createProduct(@PathVariable Long categoryId,@Valid @RequestBody ProductDTO productDTO){
        productDTO= productService.createProduct(categoryId,productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ResponseDTO<ProductDTO>> getAllProducts(@RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
                                                      @RequestParam(name = "PageSize",defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
                                                      @RequestParam(name="sortBY", defaultValue = SORT_BY)String sortBy,
                                                      @RequestParam(name="sortOrder", defaultValue = SORT_ORDER)String sortOrder) {
        ResponseDTO<ProductDTO>  responseDTO= productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ResponseDTO<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId,
                                                             @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
                                                      @RequestParam(name = "PageSize",defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
                                                      @RequestParam(name="sortBY", defaultValue = SORT_BY)String sortBy,
                                                      @RequestParam(name="sortOrder", defaultValue = SORT_ORDER)String sortOrder) {
        ResponseDTO<ProductDTO>  responseDTO= productService.getAllProductsByCategory(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ResponseDTO<ProductDTO>> getProductsByKeyword(@PathVariable String keyword,
                                                                         @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
                                                                         @RequestParam(name = "PageSize",defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
                                                                         @RequestParam(name="sortBY", defaultValue = SORT_BY)String sortBy,
                                                                         @RequestParam(name="sortOrder", defaultValue = SORT_ORDER)String sortOrder) {
        ResponseDTO<ProductDTO>  responseDTO= productService.getAllProductsByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable(name = "productId")Long id, @RequestBody ProductDTO productDTO){
        productDTO= productService.updateProducts(id,productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable(name="productId") Long id){
        ProductDTO productDTO= productService.deleteProducts(id);
        return ResponseEntity.ok(productDTO);
    }
}
