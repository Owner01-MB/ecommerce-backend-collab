package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ProductDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    private static final Logger logger = LoggerFactory.getLogger(PdfService.class);

    public byte[] generateProductsPdf(List<ProductDto> products) throws DocumentException {
        logger.info("Starting PDF generation for {} products", products.size());

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            logger.debug("Adding PDF header...");
            document.add(new Paragraph("Product List"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);

            table.addCell("ID");
            table.addCell("Name");
            table.addCell("Description");
            table.addCell("Quantity");
            table.addCell("Price");
            table.addCell("Discount");

            logger.debug("Adding {} product rows to PDF table", products.size());
            for (ProductDto product : products) {
                table.addCell(String.valueOf(product.getProductId()));
                table.addCell(product.getProductName());
                table.addCell(product.getDescription());
                table.addCell(String.valueOf(product.getQuantity()));
                table.addCell(String.valueOf(product.getPrice()));
                table.addCell(String.valueOf(product.getDiscount()));

                logger.trace("Added product to PDF: ID={}, Name={}",
                        product.getProductId(), product.getProductName());
            }

            document.add(table);
            logger.info("PDF table with product data added successfully");

        } catch (Exception e) {
            logger.error("Error occurred while generating products PDF", e);
            throw e;
        } finally {
            document.close();
        }

        byte[] pdfBytes = out.toByteArray();
        logger.info("PDF generation completed. Final size: {} bytes", pdfBytes.length);

        return pdfBytes;
    }
}
