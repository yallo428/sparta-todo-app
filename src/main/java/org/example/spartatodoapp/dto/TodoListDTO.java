package org.example.spartatodoapp.dto;

import lombok.Data;
import org.example.spartatodoapp.entity.Todo;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TodoListDTO {
    private String userName;
    private String title;

    private LocalDateTime createAt;
    private Boolean complete;

    public TodoListDTO(String userName, String title, LocalDateTime createAt, Boolean complete) {
        this.userName = userName;
        this.title = title;
        this.createAt = createAt;
        this.complete = complete;
    }
}
