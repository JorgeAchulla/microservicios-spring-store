package microservicio.spring.store.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import microservicio.spring.store.product.entity.Category;
import microservicio.spring.store.product.entity.Product;
import microservicio.spring.store.product.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductoController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam(value = "categoryId", required = false) Long categoryId) {
        List<Product> products = new ArrayList<>();
        if (categoryId == null) {
            products = productService.listAllProduct();
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if (products.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
        Product product = productService.getProduct(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product, BindingResult result) {
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
        }
        Product createProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id,
                                                 @RequestBody Product product) {
        product.setId(id);
        Product updateProduct = productService.updateProduct(product);
        if (updateProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){
        Product deleteProduct = productService.deleteProduct(id);
        if (deleteProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deleteProduct);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id,
                                                      @RequestParam("quantity") Double quantity){
        Product updateStock = productService.updateStock(id, quantity);
        if (updateStock == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateStock);
    }

    private String formatMessage(BindingResult result){
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        try{
            jsonString = objectMapper.writeValueAsString(errorMessage);

        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }
}
