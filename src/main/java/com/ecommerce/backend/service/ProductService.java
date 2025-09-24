package com.ecommerce.backend.service;

import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.ProductRepo;
import com.ecommerce.backend.service.serviceImpl.ProductImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements ProductImpl {

  @Autowired
  private ProductRepo productRepo;

  @Override
  public Object saveOrUpdateProduct(Product product) {
    if (product.getProductId() != null && productRepo.existsById(product.getProductId())) {
      Product existingProduct = productRepo.findById(product.getProductId()).get();

      existingProduct.setProductName(product.getProductName());
      existingProduct.setImage(product.getImage());
      existingProduct.setDescription(product.getDescription());
      existingProduct.setQuantity(product.getQuantity());
      existingProduct.setPrice(product.getPrice());
      existingProduct.setDiscount(product.getDiscount());
      existingProduct.setSpecialPrice(product.getSpecialPrice());

      productRepo.save(existingProduct);
      return "Updated Successfully!!!";
    } else {
      productRepo.save(product);
      return "Inserted Successfully!!!";
    }
  }

  @Override
  public Object getAllProduct() {
    return productRepo.findAll();
  }

  @Override
  public void deleteProductById(Long id) throws Exception {
    Optional<Product> optional = productRepo.findById(id);
    if (optional.isPresent()) {
      productRepo.deleteById(id);
    } else {
      throw new Exception("Id not Found!!!");
    }
  }
}
