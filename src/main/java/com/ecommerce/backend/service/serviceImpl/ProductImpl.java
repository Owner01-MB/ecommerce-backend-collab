package com.ecommerce.backend.service.serviceImpl;

import com.ecommerce.backend.model.Product;

public interface ProductImpl {
    public Object saveOrUpdateProduct(Product product);
    public Object getAllProduct();
    public void deleteProductById(Long id) throws Exception;
}
