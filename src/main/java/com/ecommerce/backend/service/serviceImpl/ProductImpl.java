package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.dto.ProductDto;
import com.ecommerce.backend.model.Product;

import java.util.List;

public interface ProductImpl {
    Object saveOrUpdateProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    void deleteProductById(Long id) throws Exception;
}
