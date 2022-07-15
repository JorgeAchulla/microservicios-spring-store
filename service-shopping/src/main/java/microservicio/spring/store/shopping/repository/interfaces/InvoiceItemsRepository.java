package microservicio.spring.store.shopping.repository.interfaces;

import microservicio.spring.store.shopping.repository.entities.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemsRepository extends JpaRepository<InvoiceItem, Long> {

}
