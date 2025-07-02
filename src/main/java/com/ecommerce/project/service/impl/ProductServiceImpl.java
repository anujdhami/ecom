package com.ecommerce.project.service.impl;

import com.ecommerce.project.DTO.ProductDTO;
import com.ecommerce.project.DTO.ResponseDTO;
import com.ecommerce.project.exception.DataNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Value("${spring.path}")
    private String path;

    @Override
    public ProductDTO createProduct(Long id, ProductDTO productDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(()->new DataNotFoundException("Category is not present"));
        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category);
        double price= (product.getDiscount()/100)*product.getPrice();
        product.setSpecialPrice(product.getPrice()-price);
        product= productRepository.save(product);
        return modelMapper.map(product,ProductDTO.class) ;
    }

    @Override
    public ResponseDTO<ProductDTO> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.fromOptionalString(sortOrder)
                .orElse(Sort.Direction.ASC), sortBy);
        Page<Product> products = productRepository.findAll(pageable);
        if(!products.hasContent()){
            throw new DataNotFoundException("Data not found");
        }
        List<ProductDTO> productDTOList = products.getContent().stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ResponseDTO<ProductDTO> responseDTO= modelMapper.map(products,ResponseDTO.class);
        responseDTO.setContent(productDTOList);
        return responseDTO;
    }

    @Override
    public ResponseDTO<ProductDTO> getAllProductsByCategory(Long id, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.fromOptionalString(sortOrder)
                .orElse(Sort.Direction.ASC), sortBy);
        Page<Product> products = productRepository.findByCategoryId(id, pageable);
        if(!products.hasContent()){
            throw new DataNotFoundException("Data not found");
        }
        List<ProductDTO> productDTOList = products.getContent().stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ResponseDTO<ProductDTO> responseDTO= modelMapper.map(products,ResponseDTO.class);
        responseDTO.setContent(productDTOList);
        return responseDTO;
    }

    @Override
    public ResponseDTO<ProductDTO> getAllProductsByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.fromOptionalString(sortOrder)
                .orElse(Sort.Direction.ASC), sortBy);
        Page<Product> products = productRepository.findByProductNameContainingIgnoreCase(keyword, pageable);
        if(!products.hasContent()){
            throw new DataNotFoundException("Data not found");
        }
        List<ProductDTO> productDTOList = products.getContent().stream().map(product -> modelMapper.map(product,
                ProductDTO.class)).toList();
        ResponseDTO<ProductDTO> responseDTO= modelMapper.map(products,ResponseDTO.class);
        responseDTO.setContent(productDTOList);
        return responseDTO;
    }

    @Override
    public ProductDTO updateProducts(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(()->new DataNotFoundException("No data found for given ID"));
        Product updatedProduct = modelMapper.map(productDTO, Product.class);
        updatedProduct.setId(product.getId());
        double price= (productDTO.getDiscount()/100)*productDTO.getPrice();
        updatedProduct.setSpecialPrice(updatedProduct.getPrice()-price);
        if(updatedProduct.getCategory()==null){
            updatedProduct.setCategory(product.getCategory());
        }
        productRepository.save(updatedProduct);
        return modelMapper.map(updatedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProducts(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No data present to delete"));
        productRepository.deleteById(id);
        return modelMapper.map(product,ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long id, MultipartFile multiPartFile) {
        Product product = productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Data not found"));
        String imageName= null;
        try {
            imageName = uploadImageName(multiPartFile);
        } catch (IOException e) {
            throw new RuntimeException("Error occur while saving image");
        }
        product.setImageName(imageName);
        productRepository.save(product);
        return modelMapper.map(product,ProductDTO.class);
    }

    @Override
    public byte[] downloadImage(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Data not found"));
        try {
            return getFileData(product).readAllBytes();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error occur while downloading file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getFileData(Product product) throws FileNotFoundException {
        String filePath= path+File.separator+product.getImageName();
        File file= new File(filePath);
        if(!file.exists()){
            throw new DataNotFoundException("Data not found");
        }
        FileInputStream inputStream= new FileInputStream(file);
        return inputStream;
    }

    private String uploadImageName(MultipartFile multiPartFile) throws IOException {
        File file= new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        String fileNewName= UUID.randomUUID().toString()+ multiPartFile.getOriginalFilename()
                .substring(multiPartFile.getOriginalFilename().lastIndexOf('.'));
        Path pathFile= Paths.get(path+File.separator+fileNewName);
            Files.copy(multiPartFile.getInputStream(),pathFile, StandardCopyOption.REPLACE_EXISTING);
        return fileNewName;
    }


}
