package microservicio.spring.store.product.repository;

import microservicio.spring.store.product.entity.Category;
import microservicio.spring.store.product.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByCategory_thenResturnListProduct() {
        Product product = Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1240.99"))
                .status("Created")
                .createAt(new Date()).build();
        productRepository.save(product);

        List<Product> founds = productRepository.findByCategory(product.getCategory());
        assertThat(founds.size()).isEqualTo(3);
    }
}
