package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ProductDto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public byte[] generateProductsPdf(List<ProductDto> products) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();
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
