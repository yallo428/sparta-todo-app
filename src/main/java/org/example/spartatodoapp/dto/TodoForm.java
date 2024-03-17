package org.example.spartatodoapp.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoForm {

    private String title;
    private String content;

    public TodoForm(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
