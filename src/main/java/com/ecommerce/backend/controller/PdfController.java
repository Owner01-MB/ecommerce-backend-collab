package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ProductDto;
import com.ecommerce.backend.service.EmailService;
import com.ecommerce.backend.service.PdfService;
import com.ecommerce.backend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PdfController {
    private static final Logger logger = LoggerFactory.getLogger(PdfController.class);

    @Autowired
    private PdfService pdfService;

    @Autowired
    private ProductService productService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/send/products/pdf")
    public String sendProductsPdfEmail() {
        try {
            logger.info("Fetching all products...");
            List<ProductDto> products = productService.getAllProducts();

            logger.info("Generating PDF for {} products", products.size());
            byte[] pdfBytes = pdfService.generateProductsPdf(products);

            logger.info("Sending email with PDF attachment...");
            emailService.sendEmailWithAttachment(
                    "snehapatil24.omsoft@gmail.com",
                    "Product List PDF",
                    "Please find attached PDF of all products.",
                    pdfBytes,
                    "products.pdf"
            );

            logger.info("Email sent successfully with products PDF");
            return "Email sent successfully!";
        } catch (Exception e) {
            logger.error("Error occurred while sending products PDF email", e);
            return "Error sending email: " + e.getMessage();
        }
    }
}
