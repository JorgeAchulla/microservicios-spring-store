package microservicio.spring.store.customer.repository.interfaces;

import microservicio.spring.store.customer.repository.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
}
