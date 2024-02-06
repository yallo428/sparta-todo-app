package org.example.spartatodoapp.dto;


import lombok.Data;

@Data
public class TodoForm {

    private String title;
    private String content;
    public TodoForm(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
