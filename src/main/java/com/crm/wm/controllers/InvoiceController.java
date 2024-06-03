package com.crm.wm.controllers;

import com.crm.wm.dto.InvoiceDetailsDTO;
import com.crm.wm.dto.InvoiceRequestDTO;
import com.crm.wm.dto.InvoiceRequestWithoutDefaultDTO;
import com.crm.wm.dto.InvoiceResponseDTO;
import com.crm.wm.dto.ProductDetailsDTO;
import com.crm.wm.entities.Invoice;
import com.crm.wm.entities.InvoiceItem;
import com.crm.wm.repository.InvoiceRepository;
import com.crm.wm.services.InvoiceService;
import com.crm.wm.services.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ReportService reportService;

    //List all the invoices, with details
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<InvoiceDetailsDTO>> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        List<InvoiceDetailsDTO> invoiceDetailsDTOs = invoices.stream()
                .map(this::mapToInvoiceDetailsDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(invoiceDetailsDTOs, HttpStatus.OK);
    }

    //List the invoices by the customer
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/list/customer/{customerId}")
    public ResponseEntity<List<InvoiceResponseDTO>> getInvoicesByCustomer(@PathVariable Long customerId) {
        List<InvoiceResponseDTO> invoices = invoiceService.getInvoicesByCustomer(customerId);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }


    //List detailed invoice by invoice ID
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/details/{invoiceId}")
    public ResponseEntity<InvoiceDetailsDTO> getInvoiceDetails(@PathVariable Long invoiceId) {
        InvoiceDetailsDTO invoiceDetailsDTO = invoiceService.getInvoiceDetails(invoiceId);
        return new ResponseEntity<>(invoiceDetailsDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<List<InvoiceResponseDTO>> generateInvoices(@RequestBody List<InvoiceRequestDTO> requestDTOs) {
        List<InvoiceResponseDTO> responseDTOs = invoiceService.generateInvoices(requestDTOs);
        return new ResponseEntity<>(responseDTOs, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/create-without-default")
    public ResponseEntity<List<InvoiceResponseDTO>> generateInvoicesWithoutDefault(@RequestBody List<InvoiceRequestWithoutDefaultDTO> requestDTOs) {
        List<InvoiceResponseDTO> responseDTOs = invoiceService.generateInvoicesWithoutDefault(requestDTOs);
        return new ResponseEntity<>(responseDTOs, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/update-is-paid/{invoiceId}")
    public ResponseEntity<String> updateIsPaidStatus(@PathVariable Long invoiceId, @RequestBody boolean isPaid) {
        invoiceService.updateIsPaidStatus(invoiceId, isPaid);
        return new ResponseEntity<>("IsPaid status updated successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/report/{invoiceId}", produces = {"application/pdf"})
    public ResponseEntity<byte[]> generateReport(@PathVariable Long invoiceId) throws IOException, JRException {
        return reportService.GeneratePdfReport(invoiceId);
    }

    // Mapping method to convert Invoice to InvoiceDetailsDTO
    private InvoiceDetailsDTO mapToInvoiceDetailsDTO(Invoice invoice) {
        InvoiceDetailsDTO dto = new InvoiceDetailsDTO();
        dto.setInvoiceId(invoice.getInvoiceID());
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setCustomerId(invoice.getCustomer() != null ? invoice.getCustomer().getCustomerID() : null);
        dto.setEmployeeId(invoice.getEmployee() != null ? invoice.getEmployee().getEmployeeID() : null);
        dto.setMonth(invoice.getMonth());
        dto.setMunicipalityId(invoice.getMunicipality() != null ? invoice.getMunicipality().getMunicipalityID() : null);
        dto.setIsPaid(invoice.getIsPaid());

        dto.setCustomerFirstName(invoice.getCustomer() != null ? invoice.getCustomer().getFirstName() : null);
        dto.setCustomerLastName(invoice.getCustomer() != null ? invoice.getCustomer().getLastName() : null);
        dto.setEmployeeFirstName(invoice.getEmployee() != null ? invoice.getEmployee().getFirstName() : null);
        dto.setEmployeeLastName(invoice.getEmployee() != null ? invoice.getEmployee().getLastName() : null);
        dto.setMunicipalityName(invoice.getMunicipality() != null ? invoice.getMunicipality().getMunicipalityName() : null);

        if (invoice.getCustomer() != null && invoice.getCustomer().getCompany() != null) {
            dto.setCompanyName(invoice.getCustomer().getCompany().getCompanyName());
        }

        dto.setProductDetailsList(invoice.getInvoiceItems() != null ? invoice.getInvoiceItems().stream()
                .map(this::mapToProductDetailsDTO)
                .collect(Collectors.toList()) : new ArrayList<>());

        return dto;
    }

    // Mapping method to convert InvoiceItem to ProductDetailsDTO
    private ProductDetailsDTO mapToProductDetailsDTO(InvoiceItem invoiceItem) {
        ProductDetailsDTO productDetailsDTO = new ProductDetailsDTO();
        productDetailsDTO.setProductName(invoiceItem.getProduct().getProductName());
        productDetailsDTO.setProductPrice(invoiceItem.getProduct().getPrice());
        productDetailsDTO.setProductDescription(invoiceItem.getProduct().getDescription());
        productDetailsDTO.setProductQuantity(invoiceItem.getQuantity());
        productDetailsDTO.setTotalPrice(invoiceItem.getQuantity() * invoiceItem.getProduct().getPrice());
        return productDetailsDTO;
    }
}
