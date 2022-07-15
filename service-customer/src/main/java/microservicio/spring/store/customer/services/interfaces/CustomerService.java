package microservicio.spring.store.customer.services.interfaces;

import microservicio.spring.store.customer.repository.entities.Customer;
import microservicio.spring.store.customer.repository.entities.Region;

import java.util.List;

public interface CustomerService {
     List<Customer> findCustomerAll();
     List<Customer> findCustomersByRegion(Region region);

     Customer createCustomer(Customer customer);
     Customer updateCustomer(Customer customer);
     Customer deleteCustomer(Customer customer);
     Customer getCustomer(Long id);
}
