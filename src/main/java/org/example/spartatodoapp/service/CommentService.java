package org.example.spartatodoapp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spartatodoapp.dto.CommentForm;
import org.example.spartatodoapp.entity.Comment;
import org.example.spartatodoapp.entity.Member;
import org.example.spartatodoapp.entity.Todo;
import org.example.spartatodoapp.exception.custom.NotAuthorityException;
import org.example.spartatodoapp.repository.CommentRepository;
import org.example.spartatodoapp.repository.MemberRepository;
import org.example.spartatodoapp.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentService {

    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;


    public void create(String username, Long id, CommentForm commentForm) {
        Member member = findMemberBy(username);

        Todo todo = findTodoBy(id);

        Comment comment = new Comment(commentForm.getContent(), member, todo);

        commentRepository.save(comment);
    }


    public void update(String userName, Long id, Long commentId, CommentForm commentForm) {
        Member member = findMemberBy(userName);

        Todo todo = findTodoBy(id);

        Comment comment = validateComment(commentId, todo, member);
        comment.update(commentForm.getContent());
    }


    public void delete(String userName, Long id, Long commentId) {
        Member member = findMemberBy(userName);

        Todo todo = findTodoBy(id);

        Comment comment = validateComment(commentId, todo, member);
        todo.removeComment(comment);
    }

    private Comment validateComment(Long commentId, Todo todo, Member member) {
        Comment comment = todo.getComments()
                .stream()
                .filter(e -> e.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글이 없습니다."));

        validateMemberMatch(member, comment);
        return comment;
    }

    private void validateMemberMatch(Member member, Comment comment) {
        if (comment.isNotMemberMatch(member)) {
            throw new NotAuthorityException();
        }
    }

    private Member findMemberBy(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    private Todo findTodoBy(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없습니다."));
    }
}
