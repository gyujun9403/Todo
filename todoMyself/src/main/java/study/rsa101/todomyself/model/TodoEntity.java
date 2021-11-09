package study.rsa101.todomyself.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import study.rsa101.todomyself.DTO.TodoDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TodoEntity {
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    private String userId;
    private String title;
    private boolean done;

    public TodoEntity(TodoDTO dto) {
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.done = dto.isDone();
    }

    public static TodoDTO entityToDTO(TodoEntity entity) {
        return TodoDTO.builder().id(entity.getId())
                .title(entity.title)
                .done(entity.isDone())
                .build();
    }
}
