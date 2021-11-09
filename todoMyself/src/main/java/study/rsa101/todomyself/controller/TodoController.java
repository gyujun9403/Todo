package study.rsa101.todomyself.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.rsa101.todomyself.DTO.ResponseDTO;
import study.rsa101.todomyself.DTO.TodoDTO;
import study.rsa101.todomyself.model.TodoEntity;
import study.rsa101.todomyself.service.TodoService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("todo")
@RestController
public class TodoController{
    @Autowired
    TodoService service;
    private String tempUserId = "temp_user_id";

    @PostMapping
    public ResponseEntity<?> TodoCreate(@RequestBody TodoDTO dto) {
        try {
            TodoEntity entity = new TodoEntity(dto);
            entity.setUserId(tempUserId);
            List<TodoDTO> dtoList = service.create(entity)
                    .stream().map(x -> TodoEntity.entityToDTO(x)).collect(Collectors.toList());
            ResponseDTO response = ResponseDTO.<TodoDTO>builder().data(dtoList).build();
            return ResponseEntity.ok(response);
        } catch(RuntimeException e) {
            String err = e.getMessage();
            log.warn(err);
            ResponseDTO response = ResponseDTO.builder().error("create error occured").build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> Todoretrieve() {
        TodoEntity tempEntity = new TodoEntity();
        tempEntity.setUserId(tempUserId);
        List<TodoDTO> dtoList = service.retrieve(tempEntity)
                .stream().map(x -> TodoEntity.entityToDTO(x)).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtoList).build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> TodoUpdate(@RequestBody TodoDTO dto) {
        TodoEntity entity = new TodoEntity(dto);
        entity.setUserId(tempUserId);
        List<TodoDTO> dtoList = service.update(entity)
                .stream().map(x -> TodoEntity.entityToDTO(x)).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtoList).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> TodoDelete(@RequestBody TodoDTO dto) {
        List<TodoDTO> dtoList = service.delete(new TodoEntity(dto))
                .stream().map(x -> TodoEntity.entityToDTO(x)).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtoList).build();
        return ResponseEntity.ok(response);
    }
}
