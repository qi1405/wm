package com.crm.wm.services;

import com.crm.wm.dto.InvoiceDetailsDTO;
import com.crm.wm.dto.ReportDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@Transactional
public class ReportService {

    @Autowired
    private InvoiceService invoiceService;
    public ResponseEntity<byte[]> GeneratePdfReport(@PathVariable Long invoiceId) throws JRException, IOException {

        // Get invoice details
        InvoiceDetailsDTO invoiceDetails = invoiceService.getInvoiceDetails(invoiceId);

        ReportDTO reportData = new ReportDTO();
        reportData.setInvoiceDetails(invoiceDetails);
        reportData.setProductDetailsList(invoiceDetails.getProductDetailsList());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(reportData));

        Map<String,Object> parameters=new HashMap<>();
//        parameters.put("createdby","JPSOLS");

        ClassPathResource classPathResource = new ClassPathResource("reports/invoice_report.jrxml");
        InputStream inputStream = classPathResource.getInputStream();

        JasperReport jasperReport = JasperCompileManager
                .compileReport(inputStream);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

        System.err.println(data);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");


        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);

    }
}
