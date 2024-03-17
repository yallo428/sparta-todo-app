package org.example.spartatodoapp.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class TodoDetailDTO {
    private String userName;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private Boolean complete;

    public TodoDetailDTO(String userName, String title, String content, LocalDateTime createAt, Boolean complete) {
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
        this.complete = complete;
    }
}
