package com.crm.wm.dto;

import lombok.Data;

@Data
public class InvoiceItemRequestDTO {
    private Long productId;
    private Integer quantity;
}

