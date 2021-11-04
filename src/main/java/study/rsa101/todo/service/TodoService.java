package study.rsa101.todo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.rsa101.todo.model.TodoEntity;
import study.rsa101.todo.persistence.TodoRepository;

import java.util.List;

@Slf4j
@Service
public class TodoService {
    @Autowired
    TodoRepository repository;

    private void validate(TodoEntity Entity) {
        if (Entity == null) {
            log.warn("Entity cannot be Null");
            throw new RuntimeException("Entity cannot be Null");
        }
        if (Entity.getUserId() == null) {
            log.warn("userId cannot be null");
            throw new RuntimeException("userId cannot be null");
        }
    }

    public List<TodoEntity> create(TodoEntity Entity) {
        validate(Entity);
        if (repository.findByUserId(Entity.getId()).isEmpty()) {
            repository.save(Entity);
            log.info("Entity userId : {} is saved", Entity.getUserId());
        }
        return repository.findByUserId(Entity.getUserId());
    }
}
