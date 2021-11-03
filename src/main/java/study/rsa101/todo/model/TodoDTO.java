package study.rsa101.todo.model;

import lombok.Data;

@Data
public class TodoDTO {
    private String userId;
    private String title;
    private boolean done;

    // 엔티티를 통해 DTO를 생성. 응답할때 사용할듯?
    public TodoDTO(TodoEntity Entity) {
        this.userId = Entity.getUserId();
        this.title = Entity.getTitle();
        this.done = Entity.isDone();
    }

    // DTO의 기능. 요청으로 받은 DTO로 엔티티를 만듦.
    public static TodoEntity getTodoEntity(TodoDTO dto) {
        return TodoEntity.builder().userId(dto.getUserId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }
}
