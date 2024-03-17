package org.example.spartatodoapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.spartatodoapp.dto.TodoForm;
import org.example.spartatodoapp.dto.SelectedTodoDTO;
import org.example.spartatodoapp.dto.TodoDetailDTO;
import org.example.spartatodoapp.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("")
    public Page<TodoDetailDTO> todoList(Pageable pageable){
        return todoService.getTodoList(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SelectedTodoDTO> selected(@PathVariable Long id){
        SelectedTodoDTO choiceTodo = todoService.getChoiceTodo(id);
        return ResponseEntity.ok(choiceTodo);

    }

    @PostMapping("")
    public ResponseEntity<TodoForm> write(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody TodoForm dto){

        TodoForm todoDTo = todoService.save(dto, userDetails.getUsername());
        return ResponseEntity.ok(todoDTo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoForm> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestBody TodoForm dto) {
        TodoForm todoDTo = todoService.update(id, dto, userDetails.getUsername());
        return ResponseEntity.ok(todoDTo);
    }


    @PutMapping("/{id}/complete")
    public ResponseEntity<String> complete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {

        todoService.complete(id, userDetails.getUsername());
        return ResponseEntity.ok("완료되었습니다.");
    }


}
