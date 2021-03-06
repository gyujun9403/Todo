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

    // ๐ข๐ข๐ข
    // entity๋ฅผ ๋ฐ๊ณ  ์ ์ฅํ๋ค. ๊ทผ๋ฐ... ํ์์ ์ฐพ์๋๋ id๊ฐ ์๋๋ผ userId๋ก ์ฐพ๋๋ค. ์?
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

    // ๐ข๐ข๐ข
    // Optional๋ก ํ๋๋ง ์์ ํ๋ค. ๊ทผ๋ฐ ์ ์ด์ retrieve๋ List๋ก ๋ฐ์์ฌ๊ฒ ์๋๋ผ Optional๋ก ๋ฐ์์์ผํ์ง ์๋??
    // ์์์  ๋ค UserId๋ก ์ฐพ์์ผ๋ฉด์ ์ฌ๊ธฐ์๋ง id๋ก ์ฐพ๋ ์ด์ ๋ ๋ญ๊น...
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

    // ๐ข๐ข๐ข
    // try catch๋ฌธ์ ์์ฑํด์ผํ๋ค...
    public List<TodoEntity> delete(TodoEntity entity) {
        validate(entity);
        try {
            repository.delete(entity);
        } catch(RuntimeException e) {
            log.warn("error deleting entity ", entity.getId(), e);
            //์ปจํธ๋กค๋ฌ๋ก exception์ ๋ ๋ฆฐ๋ค. ๋ฐ์ดํฐ๋ฒ ์ด์ค ๋ด๋ถ ๋ก์ง์ ์บก์ํ ํ๊ธฐ ์ํด e๋ฅผ ๋ฆฌํดํ์ง ์๊ณ  ์ exception ์ค๋ธ์ ํธ๋ฅผ ๋ฆฌํดํ๋ค.
            throw new RuntimeException("error deleting entity " + entity.getId());
        }
        return retrieve(entity);
    }
}
