package com.ecommerce.project.service.impl;

import com.ecommerce.project.DTO.CategoryDTO;
import com.ecommerce.project.DTO.ResponseDTO;
import com.ecommerce.project.exception.DataNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO category) {
        category.setId(null);
        Category category2 = modelMapper.map(category, Category.class);
        Category category1 = categoryRepository.save(category2);
        return modelMapper.map(category1,CategoryDTO.class);
    }

    @Override
    public ResponseDTO<CategoryDTO> getCategory(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        List<Category> list;
        Optional<Sort.Direction> direction = Sort.Direction.fromOptionalString(sortOrder);
        Sort sort= Sort.by(direction.orElse(Sort.Direction.ASC),sortBy);
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        if(CollectionUtils.isEmpty(categoryPage.getContent())){
            throw new DataNotFoundException("No data is available");
        }
        list=categoryPage.getContent();
        List<CategoryDTO> categories= list.stream().map(data-> modelMapper.map(data,CategoryDTO.class)).toList();
        ResponseDTO<CategoryDTO> responseDTO=modelMapper.map(categoryPage,ResponseDTO.class);
        responseDTO.setContent(categories);
        return responseDTO;
    }

    @Override
    public CategoryDTO deleteCategory(long id) {
        Category category;
        category= categoryRepository.findById(id).orElse(null);
        if(Objects.nonNull(category)){
            categoryRepository.deleteById(id);
        }else {
            throw new DataNotFoundException("No data is available");
        }
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(long id, CategoryDTO category) {
        Category category2 = categoryRepository.findById(id).orElse(null);
        if(Objects.nonNull(category2)){
            category2= modelMapper.map(category,Category.class);
            categoryRepository.save(category2);
            return category;
        }
        throw new DataNotFoundException("No data is available");
    }
}
