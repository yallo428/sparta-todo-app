package org.example.spartatodoapp.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spartatodoapp.dto.CommentForm;
import org.example.spartatodoapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;


    @PostMapping("/todos/{todo-id}/comments/write")
    public ResponseEntity<String> write(
            @PathVariable("todo-id") Long id,
            HttpServletRequest request,
            @RequestBody CommentForm commentForm
    ) {
        String userName = (String) request.getAttribute("userName");
        commentService.write(userName, id, commentForm);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공");
    }


    @PutMapping("/todos/{todo-id}/comments/{comment-id}/update")
    public ResponseEntity<String> update(
            @PathVariable("todo-id") Long id,
            @PathVariable("comment-id") Long commentId,
            HttpServletRequest request,
            @RequestBody CommentForm commentForm) {

        String userName = (String) request.getAttribute("userName");
        commentService.update(userName, id, commentId, commentForm);

        return ResponseEntity.ok("OK");
    }

    @DeleteMapping("/todos/{todo-id}/comments/{comment-id}/delete")
    public ResponseEntity<String> delete(
            HttpServletRequest req,
            @PathVariable("todo-id") Long id,
            @PathVariable("comment-id") Long commentId) {

        String userName = (String) req.getAttribute("userName");
        commentService.delete(userName, id, commentId);

        return ResponseEntity.ok("OK");
    }
}

