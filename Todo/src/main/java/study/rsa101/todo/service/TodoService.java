package study.rsa101.todo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.rsa101.todo.model.TodoEntity;
import study.rsa101.todo.persistence.TodoRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class TodoService {
    @Autowired
    TodoRepository repository;

    private void validate(final TodoEntity Entity) {
        if (Entity == null) {
            log.warn("Entity cannot be Null");
            throw new RuntimeException("Entity cannot be Null");
        }
        if (Entity.getUserId() == null) {
            log.warn("userId cannot be null");
            throw new RuntimeException("userId cannot be null");
        }
    }

    public List<TodoEntity> create(final TodoEntity Entity) {
        validate(Entity);
        if (repository.findByUserId(Entity.getUserId()).isEmpty()) {
            repository.save(Entity);
            log.info("Entity userId : {} is saved", Entity.getUserId());
        }
        return repository.findByUserId(Entity.getUserId());
    }

    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);
        final Optional<TodoEntity> original = repository.findById(entity.getId());
        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            repository.save(todo);
        });
        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);
        try {
            repository.delete(entity);
        } catch(Exception e) {
            log.error("error occured deleting entity", entity.getId(), e);
            throw new RuntimeException("error occured deleting entity" + entity.getId());
        }
        return retrieve(entity.getUserId());
    }
}
