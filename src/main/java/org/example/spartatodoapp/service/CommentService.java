package org.example.spartatodoapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spartatodoapp.dto.CommentForm;
import org.example.spartatodoapp.entity.Comment;
import org.example.spartatodoapp.entity.Member;
import org.example.spartatodoapp.entity.Todo;
import org.example.spartatodoapp.repository.CommentRepository;
import org.example.spartatodoapp.repository.MemberRepository;
import org.example.spartatodoapp.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentService {

    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;


    public void write(String username, Long id, CommentForm commentForm) {
        log.info("username: {}", username);
        Member member = memberRepository.findByUsername(username).orElseThrow(()
                -> new IllegalStateException("해당 유저를 찾을 수 없습니다.")
        );


        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글을 찾지 못했습니다.")
        );

        Comment comment = new Comment(commentForm.getContent(), member, todo);
        commentRepository.save(comment);
    }


    public void update(String userName, Long id, Long commentId, CommentForm commentForm) {
        Member member = memberRepository.findByUsername(userName).orElseThrow(()
                -> new IllegalStateException("해당 유저를 찾을 수 없습니다.")
        );

        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));

        Comment comment = validateComment(commentId, todo, member);
        comment.update(commentForm.getContent());
    }


    public void delete(String userName, Long id, Long commentId){
        Member member = memberRepository.findByUsername(userName).orElseThrow(()
                -> new IllegalStateException("해당 유저를 찾을 수 없습니다.")
        );

        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalStateException("해당 게시글이 없습니다."));

        Comment comment = validateComment(commentId, todo, member);
        todo.removeComment(comment);

        commentRepository.delete(comment);
    }

    private Comment validateComment(Long commentId, Todo todo, Member member) {
        Comment comment = todo.getComments()
                .stream()
                .filter(e -> e.getId().equals(commentId))
                .findFirst().orElseThrow(() -> new IllegalStateException("해당 댓글이 없습니다."));

        if (!comment.getMember().equals(member)){
            throw new IllegalArgumentException("해당 댓글을 수정할 수 없습니다.");
        }
        return comment;
    }
}
