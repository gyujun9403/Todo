package study.rsa101.todo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.rsa101.todo.model.TodoDTO;
import study.rsa101.todo.model.TodoEntity;
import study.rsa101.todo.model.TodoService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/Todo")
public class TodoController {
    @Autowired
    TodoService service;

    @PostMapping
    public ResponseEntity<?> createEntity(@RequestParam TodoDTO dto) {
        try {
            TodoEntity entity = TodoEntity.builder().userId(dto.getUserId())
                    .title(dto.getTitle())
                    .done(dto.isDone())
                    .build();
            List<TodoEntity> listEntity = service.create(entity);
            List<TodoDTO> listDTO = listEntity.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response

        } catch (RuntimeException e) {
            log.warn("");
            e.;
        }

    }

}
