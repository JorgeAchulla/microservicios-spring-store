package microservicio.spring.store.customer.repository.interfaces;

import microservicio.spring.store.customer.repository.entities.Customer;
import microservicio.spring.store.customer.repository.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByNumberID(String numberId);
    List<Customer> findByLastName(String name);
    List<Customer> findByRegion (Region region);
}
