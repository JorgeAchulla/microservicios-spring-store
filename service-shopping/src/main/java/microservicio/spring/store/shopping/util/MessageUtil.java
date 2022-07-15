package microservicio.spring.store.shopping.util;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data @Builder
public class MessageUtil {
    private String code ;
    private List<Map<String, String >> messages ;
}
