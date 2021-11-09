package study.rsa101.todomyself.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.rsa101.todomyself.model.TodoEntity;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    // 왜 아이디로 찾지?
    public List<TodoEntity> findByUserId(String userId);
}
