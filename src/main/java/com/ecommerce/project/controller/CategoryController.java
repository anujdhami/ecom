package com.ecommerce.project.controller;

import com.ecommerce.project.DTO.CategoryDTO;
import com.ecommerce.project.DTO.ResponseDTO;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ecommerce.project.config.AppConstants.*;

@RestController
@SuppressWarnings("all")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("api/admin/category")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CategoryDTO category){
        CategoryDTO categoryDTO = categoryService.createCategory(category);
        return new ResponseEntity<>(categoryDTO,HttpStatus.CREATED);
    }

    @GetMapping("api/public/categories")
    public ResponseEntity<ResponseDTO> getCategories(@RequestParam(name = "pageNumber", defaultValue = PAGE_NUMBER)int pageNumber,
                                                     @RequestParam(name="pageSize", defaultValue = PAGE_SIZE)int pageSize,
                                                     @RequestParam(name="sortBY", defaultValue = SORT_BY)String sortBy,
                                                     @RequestParam(name="sortOrder", defaultValue = SORT_ORDER)String sortOrder){
        ResponseDTO<CategoryDTO> responseDTO=categoryService.getCategory(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @DeleteMapping("api/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable @NotNull Long id){
        CategoryDTO category = categoryService.deleteCategory(id);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @PutMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("categoryId") @NotNull Long id,
                                                   @Valid @RequestBody CategoryDTO category) {
        CategoryDTO categoryDTO=categoryService.updateCategory(id,category);
        return ResponseEntity.ok().body(categoryDTO);
    }
}
