package org.example.spartatodoapp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.spartatodoapp.dto.*;
import org.example.spartatodoapp.entity.Member;
import org.example.spartatodoapp.entity.Todo;
import org.example.spartatodoapp.exception.custom.NotAuthorityException;
import org.example.spartatodoapp.repository.MemberRepository;
import org.example.spartatodoapp.repository.TodoRepository;
import org.example.spartatodoapp.repository.TodoRepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class  TodoService {

    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;
    private final TodoRepositorySupport todoRepositorySupport;


    public TodoForm save(TodoForm dto, String userName) {
        Member member = memberRepository.findByUsername(userName).orElseThrow(
                () -> new EntityNotFoundException("입력된 유저가 없습니다."));

        Todo todo = todoRepository.save(new Todo(dto.getTitle(), dto.getContent(), member));
        return todo.createTodoForm();
    }


    public SelectedTodoDTO getChoiceTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시글 입니다."));

        return todo.createSelectTodoDTO();
    }


    public Page<TodoDetailDTO> getTodoList(Pageable pageable) {
        Page<TodoDetailDTO> findAll = todoRepositorySupport.findAll(pageable);

        if(findAll.isEmpty()){
            throw new EntityNotFoundException("게시글이 없습니다.");
        }

        return findAll;

    }

//    public List<TodoDetailDTO> getTodoList() {
//        List<Todo> findAll = todoRepository.findAll();
//
//        if(findAll.isEmpty()){
//            throw new EntityNotFoundException("게시글이 없습니다.");
//        }
//
//        return findAll.stream().map(Todo::createTodoDetailDTO).toList();
//
//    }

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
                () -> new EntityNotFoundException("에러")
        );

        if (todo.isNotUserName(userName)) {
            throw new NotAuthorityException();
        }
        return todo;
    }

}
