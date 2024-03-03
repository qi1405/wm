package com.crm.wm.controllers;

import com.crm.wm.dto.InvoiceDetailsDTO;
import com.crm.wm.dto.InvoiceRequestDTO;
import com.crm.wm.dto.InvoiceRequestWithoutDefaultDTO;
import com.crm.wm.dto.InvoiceResponseDTO;
import com.crm.wm.services.InvoiceService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices() {
        List<InvoiceResponseDTO> invoices = invoiceService.getAllInvoices();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/list/customer/{customerId}")
    public ResponseEntity<List<InvoiceResponseDTO>> getInvoicesByCustomer(@PathVariable Long customerId) {
        List<InvoiceResponseDTO> invoices = invoiceService.getInvoicesByCustomer(customerId);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

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
    @PutMapping("/update-is-paid/{invoiceId}")
    public ResponseEntity<String> updateIsPaidStatus(@PathVariable Long invoiceId, @RequestBody boolean isPaid) {
        invoiceService.updateIsPaidStatus(invoiceId, isPaid);
        return new ResponseEntity<>("IsPaid status updated successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/create-without-default")
    public ResponseEntity<List<InvoiceResponseDTO>> generateInvoicesWithoutDefault(@RequestBody List<InvoiceRequestWithoutDefaultDTO> requestDTOs) {
        List<InvoiceResponseDTO> responseDTOs = invoiceService.generateInvoicesWithoutDefault(requestDTOs);
        return new ResponseEntity<>(responseDTOs, HttpStatus.CREATED);
    }

    @GetMapping("/{invoiceId}/pdf")
    public ResponseEntity<byte[]> getInvoiceDetailsAsPdf(@PathVariable Long invoiceId) {
        try {
            // Get the invoice details
            InvoiceDetailsDTO invoiceDetails = invoiceService.getInvoiceDetails(invoiceId);

            // Convert the invoice details to a list (as required by the JasperReports data source)
            List<InvoiceDetailsDTO> detailsList = new ArrayList<>();
            detailsList.add(invoiceDetails);

            // Load the JasperReport template (assuming it's in the classpath)
            InputStream reportStream = this.getClass().getResourceAsStream("/FirstReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Create a data source from the invoice details
            JRDataSource dataSource = new JRBeanCollectionDataSource(detailsList);

            // Add any necessary parameters (in this case, none)
            Map<String, Object> parameters = new HashMap<>();

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export the report to a PDF file
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            // Return the PDF file
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header("Content-Disposition", "attachment; filename=Invoice_" + invoiceId + ".pdf")
                    .body(pdf);

        } catch (JRException e) {
            // Handle the exception
            throw new RuntimeException("Error generating PDF", e);
        }
    }

}