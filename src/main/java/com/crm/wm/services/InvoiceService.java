package com.crm.wm.services;

import com.crm.wm.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceService {
    InvoiceDTO generateInvoice(Long customerId, Long employeeId, List<Long> productIds);
}
