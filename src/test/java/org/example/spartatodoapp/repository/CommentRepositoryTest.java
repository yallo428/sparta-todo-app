package org.example.spartatodoapp.repository;

import org.example.spartatodoapp.entity.Comment;
import org.example.spartatodoapp.entity.Member;
import org.example.spartatodoapp.entity.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableJpaAuditing
@Transactional
class CommentRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    CommentRepository commentRepository;


    @Test
    @Rollback(value = false)
    void 댓글쓰기(){
        Member member = memberRepository.findById(1L).orElseThrow();
        Todo todo = todoRepository.findById(1L).orElseThrow();
        Comment comment = new Comment("test", member, todo);
        commentRepository.save(comment);
    }
}