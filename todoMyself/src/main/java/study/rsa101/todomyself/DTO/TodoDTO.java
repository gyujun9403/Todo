package study.rsa101.todomyself.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TodoDTO {
    private String id;
    private String title;
    private boolean done;
}
