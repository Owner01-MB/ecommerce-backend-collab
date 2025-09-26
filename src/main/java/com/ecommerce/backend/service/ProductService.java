package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ProductDto;
import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.ProductRepo;
import com.ecommerce.backend.service.serviceImpl.ProductImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductImpl {

  @Autowired
  private ProductRepo productRepo;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Override
    public Object saveOrUpdateProduct(ProductDto productDto) {
        if (productDto.getProductId() != null && productRepo.existsById(productDto.getProductId())) {
            Product existingProduct = productRepo.findById(productDto.getProductId()).get();
            existingProduct.setProductName(productDto.getProductName());
            existingProduct.setDescription(productDto.getDescription());
            existingProduct.setQuantity(productDto.getQuantity());
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setDiscount(productDto.getDiscount());
            existingProduct.setSpecialPrice(productDto.getSpecialPrice());
            productRepo.save(existingProduct);
            logger.info("Product updated successfully with ID: {}", productDto.getProductId());
            return "Updated Successfully!!!";
        } else {
            Product newProduct = new Product();
            newProduct.setProductName(productDto.getProductName());
            newProduct.setDescription(productDto.getDescription());
            newProduct.setQuantity(productDto.getQuantity());
            newProduct.setPrice(productDto.getPrice());
            newProduct.setDiscount(productDto.getDiscount());
            newProduct.setSpecialPrice(productDto.getSpecialPrice());
            productRepo.save(newProduct);
            logger.info("New Product inserted with ID: {}", newProduct.getProductId());
            return "Inserted Successfully!!!";
        }
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepo.findAll();
        logger.info("Fetched all products, count: {}", products.size());

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto dto = new ProductDto();
            dto.setProductId(product.getProductId());
            dto.setProductName(product.getProductName());
            dto.setDescription(product.getDescription());
            dto.setQuantity(product.getQuantity());
            dto.setPrice(product.getPrice());
            dto.setDiscount(product.getDiscount());
            dto.setSpecialPrice(product.getSpecialPrice());
            productDtos.add(dto);
        }
        return productDtos;
    }

    @Override
    public void deleteProductById(Long id) throws Exception {
        logger.info("Delete requested for product ID: {}", id);
        Optional<Product> optional = productRepo.findById(id);
        if (optional.isPresent()) {
            productRepo.deleteById(id);
            logger.info("Product with ID {} has been deleted successfully.", id);
        } else {
            logger.error("Product not found with ID: {}", id);
            throw new Exception("Id not Found!!!");
        }
    }

    @Override
    public byte[] generateProductsPdf() throws DocumentException {
        List<ProductDto> products = getAllProducts(); // existing method वापरून data fetch

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        document.add(new Paragraph("Product List"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(6);
        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Description");
        table.addCell("Quantity");
        table.addCell("Price");
        table.addCell("Discount");

        for (ProductDto product : products) {
            table.addCell(String.valueOf(product.getProductId()));
            table.addCell(product.getProductName());
            table.addCell(product.getDescription());
            table.addCell(String.valueOf(product.getQuantity()));
            table.addCell(String.valueOf(product.getPrice()));
            table.addCell(String.valueOf(product.getDiscount()));
        }

        document.add(table);
        document.close();

        return out.toByteArray();
    }
}
