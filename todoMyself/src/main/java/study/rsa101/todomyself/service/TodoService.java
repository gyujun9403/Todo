package study.rsa101.todomyself.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.rsa101.todomyself.model.TodoEntity;
import study.rsa101.todomyself.persistence.TodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    private void validate(TodoEntity entity) {
        if (entity == null) {
            throw new RuntimeException("entity cannot be null");
        }
        if (entity.getUserId() == null) {
            throw new RuntimeException("entity.userId cannot be null");
        }
    }

    // 😢😢😢
    // entity를 받고 저장한다. 근데... 회원을 찾을때는 id가 아니라 userId로 찾는다. 왜?
    public List<TodoEntity> create(TodoEntity entity) {
        validate(entity);
        if (repository.findByUserId(entity.getUserId()).isEmpty())
            repository.save(entity);
        return repository.findByUserId(entity.getUserId()).stream().collect(Collectors.toList());
    }

    public List<TodoEntity> retrieve(TodoEntity entity) {
        validate(entity);
        return repository.findByUserId(entity.getUserId());
    }

    // 😢😢😢
    // Optional로 하나만 수정한다. 근데 애초에 retrieve도 List로 받아올게 아니라 Optional로 받아와야하지 않나??
    // 위에선 다 UserId로 찾았으면서 여기서만 id로 찾는 이유는 뭘까...
    public List<TodoEntity> update(TodoEntity entity) {
        validate(entity);
        Optional<TodoEntity> old = repository.findById(entity.getId());
        old.ifPresent(x -> {
            x.setTitle(entity.getTitle());
            x.setDone(entity.isDone());
            repository.save(x);
        });
        return retrieve(entity);
    }

    public List<TodoEntity> delete(TodoEntity entity) {
        validate(entity);
        repository.delete(entity);
        return retrieve(entity);
    }
}
