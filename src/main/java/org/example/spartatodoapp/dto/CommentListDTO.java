package org.example.spartatodoapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentListDTO {
    private String userName;
    private String content;
    private LocalDateTime createAt;

    public CommentListDTO(String userName, String content, LocalDateTime createAt) {
        this.userName = userName;
        this.content = content;
        this.createAt = createAt;
    }
}
