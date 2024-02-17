package com.crm.wm.services;

import com.crm.wm.dto.InvoiceDTO;
import com.crm.wm.entities.Customer;
import com.crm.wm.entities.Employee;
import com.crm.wm.entities.Invoice;
import com.crm.wm.entities.InvoiceItem;
import com.crm.wm.entities.Product;
import com.crm.wm.repository.CustomerRepository;
import com.crm.wm.repository.EmployeeRepository;
import com.crm.wm.repository.InvoiceRepository;
import com.crm.wm.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public InvoiceDTO generateInvoice(Long customerId, Long employeeId, List<Long> productIds) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }

        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }

        // Fetch entities from the database
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));


        List<Product> products = productRepository.findAllById(productIds);

        // Create a new Invoice
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setEmployee(employee);
        invoice.setInvoiceDate(new Date());
        invoice.setMonth(new Date());  // You might want to set this to a specific value

        // Set products to the invoice items
        List<InvoiceItem> invoiceItems = products.stream()
                .map(product -> new InvoiceItem(product, 1))  // Assuming quantity is 1, adjust as needed
                .collect(Collectors.toList());

        invoice.setInvoiceItems(invoiceItems);

        // Calculate total amount
        double totalAmount = invoiceItems.stream()
                .mapToDouble(InvoiceItem::getTotalPrice)
                .sum();
        invoice.setTotalAmount(totalAmount);

        // Save the invoice to the database
        invoiceRepository.save(invoice);

        // Return the DTO
        return new InvoiceDTO(invoice);
    }
}
