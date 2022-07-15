package microservicio.spring.store.shopping.services.interfaces;

import microservicio.spring.store.shopping.repository.entities.Invoice;

import java.util.List;

public interface InvoiceService {
    List<Invoice> findInvoiceAll();

    Invoice createInvoice(Invoice invoice);
    Invoice updateInvoice(Invoice invoice);
    Invoice deleteInvoice(Invoice invoice);

    Invoice getInvoice(Long id);
}
