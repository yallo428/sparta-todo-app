package org.example.spartatodoapp.service;

import lombok.RequiredArgsConstructor;
import org.example.spartatodoapp.dto.CommentListDTO;
import org.example.spartatodoapp.dto.TodoForm;
import org.example.spartatodoapp.dto.SelectedTodoDTO;
import org.example.spartatodoapp.dto.TodoListDTO;
import org.example.spartatodoapp.entity.Member;
import org.example.spartatodoapp.entity.Todo;
import org.example.spartatodoapp.repository.MemberRepository;
import org.example.spartatodoapp.repository.TodoRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class  TodoService {

    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;

    public TodoForm save(TodoForm dto, String userName) {
        Member member = memberRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalStateException("입력된 유저가 없습니다."));

        Todo todo = todoRepository.save(new Todo(dto.getTitle(), dto.getContent(), member));
        return new TodoForm(todo.getTitle(), todo.getContent());
    }


    public SelectedTodoDTO getChoiceTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 게시글 입니다."));

        List<CommentListDTO> commentList = todo.getComments().stream().map(comment -> new CommentListDTO(
                comment.getMember().getUsername(),
                comment.getContent(),
                comment.getCreateAt()
        )).toList();

        return new SelectedTodoDTO(
                todo.getMember().getUsername(),
                todo.getTitle(),
                todo.getContent(),
                todo.getCreateAt(),
                commentList
        );
    }

    public List<TodoListDTO> getTodoList() {
        List<Todo> findAll = todoRepository.findAll();

        if(findAll.isEmpty()){
            throw new IllegalArgumentException("게시글이 없습니다.");
        }

        return findAll.stream().map(
                todo -> new TodoListDTO(
                        todo.getMember().getUsername(),
                        todo.getTitle(),
                        todo.getCreateAt(),
                        todo.getComplete())).toList();
    }

    @Transactional
    public TodoForm update(Long id, TodoForm dto, String userName) {
        Todo todo = validateMember(userName, id);
        todo.update(dto.getTitle(), dto.getContent());
        return new TodoForm(todo.getTitle(), todo.getContent());
    }


    @Transactional
    public void complete(Long id, String userName) {
        Todo todo = validateMember(userName , id);
        todo.complete();
    }

    private Todo validateMember(String userName, Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("에러")
        );

        if (todo.isNotUserName(userName)) {
            throw new IllegalArgumentException("해당 게시물은 수정할 수 없습니다.");
        }
        return todo;
    }

}
