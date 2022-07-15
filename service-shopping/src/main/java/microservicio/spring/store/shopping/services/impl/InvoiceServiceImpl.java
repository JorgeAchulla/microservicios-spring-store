package microservicio.spring.store.shopping.services.impl;

import lombok.RequiredArgsConstructor;
import microservicio.spring.store.shopping.repository.entities.Invoice;
import microservicio.spring.store.shopping.repository.interfaces.InvoiceItemsRepository;
import microservicio.spring.store.shopping.repository.interfaces.InvoiceRepository;
import microservicio.spring.store.shopping.services.interfaces.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemsRepository invoiceItemsRepository;

    @Override
    public List<Invoice> findInvoiceAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());
        if (invoiceDB != null){
            return invoiceDB;
        }
        invoice.setState("CREATED");
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice updateInvoice = getInvoice(invoice.getId());
        if (updateInvoice == null){
            return null;
        }
        updateInvoice.setCustomerId(invoice.getCustomerId());
        updateInvoice.setDescription(invoice.getDescription());
        updateInvoice.setNumberInvoice(invoice.getNumberInvoice());
        updateInvoice.getItems().clear();
        updateInvoice.setItems(invoice.getItems());
        return invoiceRepository.save(updateInvoice);
    }

    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice deleteInvoice = getInvoice(invoice.getId());
        if (deleteInvoice == null){
            return null;
        }
        deleteInvoice.setState("DELETED");
        return invoiceRepository.save(deleteInvoice);
    }

    @Override
    public Invoice getInvoice(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }
}
