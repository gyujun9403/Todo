package study.rsa101.todo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import study.rsa101.todo.dto.TodoDTO;
import study.rsa101.todo.dto.ResponseDTO;
import study.rsa101.todo.model.TodoEntity;
import study.rsa101.todo.service.TodoService;

import java.util.List;
import java.util.stream.Collectors;

//@Slf4j
@RestController
@RequestMapping("todo")
public class TodoController {
    @Autowired
    TodoService service;

    @PostMapping
    public ResponseEntity<?> createEntity(@RequestBody TodoDTO dto) {
        try {
            // dto를 이용해 entity를 생성
            TodoEntity entity = TodoDTO.getTodoEntity(dto);
            // 추후 제거
            entity.setUserId("TestUserId");
            entity.setDone(false);
            entity.setId(null);

            List<TodoEntity> listEntity = service.create(entity);
            List<TodoDTO> listDTO = listEntity.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(listDTO).build();
            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }

    }

    @GetMapping
    public ResponseEntity<?> retrieveTodo(@RequestParam(required = true) String userId) {

        // 검색한 데이터가 없어도 딱히 에러가 일어나진 않으므로... try catch문을 사용할 필요는 없는듯하다?ㄴ
        List<TodoEntity> listEntity = service.retrieve(userId);
        List<TodoDTO> listDTO = listEntity.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(listDTO).build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
        TodoEntity entity = TodoDTO.getTodoEntity(dto);
        // 추후 제거
        entity.setUserId("TestUserId");

        List<TodoEntity> entityList = service.update(entity);
        List<TodoDTO> dtoList = entityList.stream().map(x -> new TodoDTO(x)).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtoList).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto) {
        TodoEntity entity = TodoDTO.getTodoEntity(dto);
        // 추후 제거
        entity.setUserId("TestUserId");

        List<TodoEntity> entityList = service.delete(entity);
        List<TodoDTO> dtoList = entityList.stream().map(x -> new TodoDTO(x)).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtoList).build();
        return ResponseEntity.ok(response);
    }
}
