package com.crm.wm.dto;

import com.crm.wm.entities.InvoiceItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemDTO {

    private Long invoiceItemID;
    private ProductDTO product;
    private Double price;
    private Integer quantity;
    private Double totalPrice;

    public InvoiceItemDTO(InvoiceItem invoiceItem) {
        this.invoiceItemID = invoiceItem.getInvoiceItemID();
        this.product = new ProductDTO(invoiceItem.getProduct());
        this.price = invoiceItem.getPrice();
        this.quantity = invoiceItem.getQuantity();
        this.totalPrice = invoiceItem.getTotalPrice();
    }

    // Convert a list of InvoiceItems to DTOs
    public static List<InvoiceItemDTO> convertToDTOList(List<InvoiceItem> invoiceItems) {
        return invoiceItems.stream().map(InvoiceItemDTO::new).collect(Collectors.toList());
    }
}
