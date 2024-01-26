package org.example.spartatodoapp.repository;

import org.example.spartatodoapp.entity.Member;
import org.example.spartatodoapp.entity.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableJpaAuditing
@Transactional
class TodoRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TodoRepository todoRepository;


    @Test
    @Rollback(value = false)
    void save(){
        Member member = memberRepository.findById(1L).orElseThrow();

        Todo todo = new Todo("title", "test", false, member);
        todoRepository.save(todo);
    }

    @Test
    @Rollback(value = false)
    void update(){
        Todo todo = todoRepository.findById(1L).orElseThrow();

        todo.update("title2", "test2");
    }


    @Test
    @Rollback(value = false)
    void findAll(){
        Member member = memberRepository.findById(1L).orElseThrow();
        List<Todo> allByMember = todoRepository.findAllByMember(member);

        for (Todo todo : allByMember) {
            System.out.println("todo = " + todo);
        }
    }

}