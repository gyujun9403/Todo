package study.rsa101.todo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    @Query("select * from Todo t where t.userId = ?1")
    public List<TodoEntity> findByUserId(String userId);
}
