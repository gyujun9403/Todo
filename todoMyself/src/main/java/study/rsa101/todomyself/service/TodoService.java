package study.rsa101.todomyself.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.rsa101.todomyself.model.TodoEntity;
import study.rsa101.todomyself.persistence.TodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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

    // 😢😢😢
    // try catch문을 작성해야한다...
    public List<TodoEntity> delete(TodoEntity entity) {
        validate(entity);
        try {
            repository.delete(entity);
        } catch(RuntimeException e) {
            log.warn("error deleting entity ", entity.getId(), e);
            //컨트롤러로 exception을 날린다. 데이터베이스 내부 로직을 캡슐화 하기 위해 e를 리턴하지 않고 새 exception 오브젝트를 리턴한다.
            throw new RuntimeException("error deleting entity " + entity.getId());
        }
        return retrieve(entity);
    }
}
