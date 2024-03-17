package org.example.spartatodoapp.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.spartatodoapp.dto.TodoDetailDTO;
import org.example.spartatodoapp.entity.QTodo;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.spartatodoapp.entity.QTodo.todo;

@Repository
@RequiredArgsConstructor
public class TodoRepositorySupport {

    private final JPAQueryFactory factory;

    public List<TodoDetailDTO> findAll(){
        return factory.query()
                .select(
                        Projections.fields(
                                TodoDetailDTO.class,
                                todo.member.username,
                                todo.title,
                                todo.content,
                                todo.createAt,
                                todo.complete
                        )
                )
                .from(todo)
                .fetch();
    }
}
