package com.crm.wm.services;

import com.crm.wm.dto.InvoiceDetailsDTO;
import com.crm.wm.dto.ReportDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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

    @Autowired
    private ResourceLoader resourceLoader;

    public void compileReport() throws JRException, IOException {
        Resource resource = resourceLoader.getResource("classpath:reports/product_report.jrxml");
        String jasperReportPath = resource.getFile().getParent() + "/product_report.jasper";
        JasperCompileManager.compileReportToFile(resource.getFile().getAbsolutePath(), jasperReportPath);
    }

    public ResponseEntity<byte[]> GeneratePdfReport(@PathVariable Long invoiceId) throws JRException, IOException {

        // Compile the subreport
        compileReport();

        // Get invoice details
        InvoiceDetailsDTO invoiceDetails = invoiceService.getInvoiceDetails(invoiceId);

        ReportDTO reportData = new ReportDTO();
        reportData.setInvoiceDetails(invoiceDetails);
        reportData.setProductDetailsList(invoiceDetails.getProductDetailsList());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(reportData));

        Map<String,Object> parameters=new HashMap<>();
        // parameters.put("createdby","JPSOLS");

        // Compile the main report
        ClassPathResource mainReportResource = new ClassPathResource("reports/invoice_report.jrxml");
        InputStream mainReportStream = mainReportResource.getInputStream();
        JasperReport mainReport = JasperCompileManager.compileReport(mainReportStream);

        // Compile the subreport
        ClassPathResource subReportResource = new ClassPathResource("reports/product_report.jrxml");
        InputStream subReportStream = subReportResource.getInputStream();
        JasperReport subReport = JasperCompileManager.compileReport(subReportStream);

        // Add the compiled subreport to the parameters
        parameters.put("SubReportParameter", subReport);

        JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, dataSource);

        byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);

        System.err.println(data);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
    }

}
