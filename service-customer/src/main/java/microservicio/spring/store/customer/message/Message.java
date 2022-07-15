package microservicio.spring.store.customer.message;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class Message {
    private String code;
    private List<Map<String, String>> messages;
}
