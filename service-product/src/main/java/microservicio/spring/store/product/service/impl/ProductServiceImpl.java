package microservicio.spring.store.product.service.impl;

import lombok.RequiredArgsConstructor;
import microservicio.spring.store.product.entity.Category;
import microservicio.spring.store.product.entity.Product;
import microservicio.spring.store.product.repository.ProductRepository;
import microservicio.spring.store.product.service.interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product updateProduct = productRepository.getById(product.getId());
        if (updateProduct == null){
            return null;
        }
        updateProduct.setName(product.getName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setCategory(product.getCategory());
        updateProduct.setPrice(product.getPrice());
        return productRepository.save(updateProduct);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new NullPointerException("no se encontro el producto"));

        product.setStatus("DELETED");

        return productRepository.save(product);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new NullPointerException("no se encontro el producto"));

        Double stockActual = product.getStock() + quantity;
        product.setStock(stockActual);
        return productRepository.save(product);
    }
}
