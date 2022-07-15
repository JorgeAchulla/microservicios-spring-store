package microservicio.spring.store.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import microservicio.spring.store.customer.message.Message;
import microservicio.spring.store.customer.repository.entities.Customer;
import microservicio.spring.store.customer.repository.entities.Region;
import microservicio.spring.store.customer.services.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> listAllCustomer(@RequestParam(value = "regionId", required = false) Long regionId){
        List<Customer> customers;
        if (regionId == null){
            customers = customerService.findCustomerAll();
            if (customers.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else {
            Region region = new Region();
            region.setId(regionId);
            customers = customerService.findCustomersByRegion(region);
            if (customers == null){
                log.error("Clientes con Region id {} no encontrada.", regionId);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
        log.info("Obteniendo cliente con id {}", id);
        Customer customerDB = customerService.getCustomer(id);
        if (customerDB == null){
            log.info("cliente con id {}  no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerDB);
    }

    @PostMapping
    public ResponseEntity<Customer> createdCustomer(@RequestBody @Valid Customer customer,
                                                    BindingResult result){
        log.info("Crear Cliente: {}", customer);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
        }
        Customer customerBD = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerBD);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id,
                                                   @RequestBody Customer customer){
        log.info("Actualizar Cliente con id: {}", id);
        Customer updateCustomer = customerService.getCustomer(id);
        if (updateCustomer == null){
            log.info("no se puede actualizar, cliente con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        customer.setId(id);
        updateCustomer = customerService.updateCustomer(customer);
        return ResponseEntity.ok(updateCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") Long id){
        log.info("Obtener y eliminar cliente con id {} ", id);
        Customer deleteCustomer = customerService.getCustomer(id);
        if (deleteCustomer == null){
            log.info("No se puede eliminar. Cliente con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        deleteCustomer = customerService.deleteCustomer(deleteCustomer);
        return ResponseEntity.ok(deleteCustomer);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        Message errorMessage = Message.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
