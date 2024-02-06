package org.example.spartatodoapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.spartatodoapp.dto.TodoForm;
import org.example.spartatodoapp.dto.SelectedTodoDTO;
import org.example.spartatodoapp.dto.TodoListDTO;
import org.example.spartatodoapp.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("")
    public ResponseEntity<List<TodoListDTO>> todoList(){
        List<TodoListDTO> todoList = todoService.getTodoList();
        return ResponseEntity.ok(todoList);
    }
    @GetMapping("/selected/{id}")
    public ResponseEntity<SelectedTodoDTO> selected(@PathVariable Long id){

        SelectedTodoDTO choiceTodo = todoService.getChoiceTodo(id);
        return ResponseEntity.ok(choiceTodo);

    }
    @PostMapping("/write")
    public ResponseEntity<TodoForm> write(
            HttpServletRequest req,
            @RequestBody TodoForm dto){

        String userName = (String) req.getAttribute("userName");
        TodoForm todoDTo = todoService.save(dto, userName);
        return ResponseEntity.ok(todoDTo);
    }

    @PutMapping("/selected/{id}/update")
    public ResponseEntity<TodoForm> update(
            HttpServletRequest req,
            @PathVariable Long id,
            @RequestBody TodoForm dto) {
        String userName = (String) req.getAttribute("userName");
        TodoForm todoDTo = todoService.update(id, dto, userName);
        return ResponseEntity.ok(todoDTo);
    }


    @PutMapping("/selected/{id}/complete")
    public ResponseEntity<String> complete(
            HttpServletRequest req,
            @PathVariable Long id) {
        String userName = (String) req.getAttribute("userName");
        todoService.complete(id, userName);
        return ResponseEntity.ok("완료되었습니다.");
    }


}
