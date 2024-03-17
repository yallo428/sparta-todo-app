package org.example.spartatodoapp.service;

import org.assertj.core.api.Assertions;
import org.example.spartatodoapp.dto.CommentDetailDTO;
import org.example.spartatodoapp.dto.SelectedTodoDTO;
import org.example.spartatodoapp.dto.TodoForm;
import org.example.spartatodoapp.dto.TodoDetailDTO;
import org.example.spartatodoapp.entity.Comment;
import org.example.spartatodoapp.entity.Member;
import org.example.spartatodoapp.entity.Todo;
import org.example.spartatodoapp.repository.MemberRepository;
import org.example.spartatodoapp.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoService todoService;

    @Test
    void 글_저장(){
        Member member = new Member("test", "1234");
        Todo todo = new Todo("테스트", "테스트", member);

        TodoForm todoForm = new TodoForm("테스트", "테스트");

        given(memberRepository.findByUsername(member.getUsername())).willReturn(Optional.of(member));
        given(todoRepository.save(any(Todo.class))).willReturn(todo);

        TodoForm saveDto = todoService.save(todoForm, member.getUsername());

        Assertions.assertThat(todoForm).isEqualTo(saveDto);
    }

    @Test
    void 단일_조회(){
        Long id = 1L;
        Member member = new Member("테스트", "1234");
        Todo todo = new Todo("테스트", "테스트", member);
        Comment comment = new Comment("댓글입니다.", member, todo);

        todo.addComment(comment);

        List<CommentDetailDTO> commentList = todo.getComments().stream().map(sample -> new CommentDetailDTO(
                sample.getMember().getUsername(),
                sample.getContent(),
                sample.getCreateAt()
        )).toList();

        SelectedTodoDTO selectedTodoDTO =  new SelectedTodoDTO(
                todo.getMember().getUsername(),
                todo.getTitle(),
                todo.getContent(),
                todo.getCreateAt(),
                commentList
        );

        given(todoRepository.findById(id)).willReturn(Optional.of(todo));
        SelectedTodoDTO result = todoService.getChoiceTodo(id);
        Assertions.assertThat(selectedTodoDTO).isEqualTo(result);
    }

    @Test
    void 전체_조회(){
        Member member = new Member("test", "1234");
        List<Todo> todos = new ArrayList<>();

        todos.add(new Todo("테스트", "테스트", member));
        todos.add(new Todo("테스트", "테스트", member));
        todos.add(new Todo("테스트", "테스트", member));
        todos.add(new Todo("테스트", "테스트", member));

        List<TodoDetailDTO> todoDetailDTOS = todos.stream().map(
                todo -> new TodoDetailDTO(
                        todo.getMember().getUsername(),
                        todo.getTitle(),
                        todo.getCreateAt(),
                        todo.getComplete())).toList();

        given(todoRepository.findAll()).willReturn(todos);

        List<TodoDetailDTO> result = todoService.getTodoList();

        Assertions.assertThat(todoDetailDTOS).isEqualTo(result);
    }

    @Test
    void 전체_조회_게시물_없을때(){
        Member member = new Member("test", "1234");
        List<Todo> todos = new ArrayList<>();

        given(todoRepository.findAll()).willReturn(todos);

        Assertions.assertThatThrownBy(()->
                todoService.getTodoList())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("게시글이 없습니다.");
    }




}