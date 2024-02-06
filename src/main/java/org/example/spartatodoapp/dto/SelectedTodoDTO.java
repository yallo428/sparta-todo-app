package org.example.spartatodoapp.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SelectedTodoDTO {
    private String userName;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private List<CommentListDTO> comments;

    public SelectedTodoDTO(String userName, String title, String content,LocalDateTime createAt,  List<CommentListDTO> comments) {
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.comments = comments;
    }
}
