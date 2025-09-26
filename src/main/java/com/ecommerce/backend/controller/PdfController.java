package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ProductDto;
import com.ecommerce.backend.service.EmailService;
import com.ecommerce.backend.service.PdfService;
import com.ecommerce.backend.service.ProductService;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PdfController {
    @Autowired
    private PdfService pdfService;

    @Autowired
    private ProductService productService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/send/products/pdf")
    public String sendProductsPdfEmail() {
        try {
            // 1️⃣ Get all products
            List<ProductDto> products = productService.getAllProducts();

            // 2️⃣ Generate PDF
            byte[] pdfBytes = pdfService.generateProductsPdf(products);

            // 3️⃣ Send email
            emailService.sendEmailWithAttachment(
                    "snehajpatil0624@gmail.com",
                    "Product List PDF",
                    "Please find attached PDF of all products.",
                    pdfBytes,
                    "products.pdf"
            );

            return "Email sent successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error sending email: " + e.getMessage();
        }
    }
}
