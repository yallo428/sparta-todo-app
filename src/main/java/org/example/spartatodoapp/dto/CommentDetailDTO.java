package org.example.spartatodoapp.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDetailDTO {
    private String userName;
    private String content;
    private LocalDateTime createAt;

    public CommentDetailDTO(String userName, String content, LocalDateTime createAt) {
        this.userName = userName;
        this.content = content;
        this.createAt = createAt;
    }
}
