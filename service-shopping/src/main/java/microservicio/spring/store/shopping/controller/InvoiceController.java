package microservicio.spring.store.shopping.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import microservicio.spring.store.shopping.repository.entities.Invoice;
import microservicio.spring.store.shopping.services.interfaces.InvoiceService;
import microservicio.spring.store.shopping.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<Invoice>> listInvoiceAll(){
        List<Invoice> invoices = invoiceService.findInvoiceAll();
        if (invoices.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable("id") Long id){
        log.info("Obteniendo Factura con {} id", id);
        Invoice invoice = invoiceService.getInvoice(id);
        if (invoice == null){
            log.error("Factura con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(invoice);
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice, BindingResult result){
        log.info("crear factura: {} ", invoice);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
        }

        Invoice createInvoice = invoiceService.createInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body(createInvoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable("id") Long id,
                                                 @RequestBody Invoice invoice){
        log.info("udating invoice con id {} ", id);
        invoice.setId(id);
        Invoice updateInvoice = invoiceService.updateInvoice(invoice);
        if (updateInvoice == null) {
            log.error("no se puede actualizar, factura con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable("id") Long id){
        log.info("Obtener y eliminar factura con id {}", id);
        Invoice deleteInvoice = invoiceService.getInvoice(id);
        if (deleteInvoice == null){
            log.error("No se puede eliminar. Factura con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        deleteInvoice = invoiceService.deleteInvoice(deleteInvoice);
        return ResponseEntity.ok(deleteInvoice);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        MessageUtil errorMessage = MessageUtil.builder()
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
