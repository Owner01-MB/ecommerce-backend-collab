package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ProductDto;
import com.ecommerce.backend.exception.PdfGenerationException;
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
            List<ProductDto> products = productService.getAllProducts();
            byte[] pdfBytes = pdfService.generateProductsPdf(products);

            emailService.sendEmailWithAttachment(
                    "snehapatil24.omsoft@gmail.com",
                    "Product List PDF",
                    "Please find attached PDF of all products.",
                    pdfBytes,
                    "products.pdf"
            );

            return "Email sent successfully!";
        } catch (PdfGenerationException e) {
            logger.error("PDF generation failed", e);
            return "Error generating PDF: " + e.getMessage();
        } catch (Exception e) {
            logger.error("Other error occurred while sending email", e);
            return "Error sending email: " + e.getMessage();
        }
    }

}
