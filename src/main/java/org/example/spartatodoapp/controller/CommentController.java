package org.example.spartatodoapp.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spartatodoapp.dto.CommentForm;
import org.example.spartatodoapp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/todos/{todoId}/comments/")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("")
    public ResponseEntity<String> write(
            @PathVariable("todoId") Long id,
            @RequestBody CommentForm commentForm,
            @AuthenticationPrincipal UserDetails userDetails
            ) {
        commentService.create(userDetails.getUsername(), id, commentForm);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공");
    }


    @PutMapping("/{commentId}/update")
    public ResponseEntity<String> update(
            @PathVariable("todoId") Long id, @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CommentForm commentForm) {

        commentService.update(userDetails.getUsername(), id, commentId, commentForm);

        return ResponseEntity.ok("OK");
    }

    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<String> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("todoId") Long id,
            @PathVariable("commentId") Long commentId) {

        commentService.delete(userDetails.getUsername(), id, commentId);

        return ResponseEntity.ok("OK");
    }
}

