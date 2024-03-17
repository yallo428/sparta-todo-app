package org.example.spartatodoapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.spartatodoapp.dto.CommentDetailDTO;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public Comment(String content, Member member, Todo todo) {
        this.content = content;
        this.member = member;
        todo.addComment(this);
    }

    public void update(String content){
        this.content = content;
    }

    public CommentDetailDTO createCommentDto(){
        return CommentDetailDTO.builder()
                .userName(this.member.getUsername())
                .content(this.content)
                .createAt(this.getCreateAt())
                .build();
    }

    public boolean isNotMemberMatch(Member member){
        return !this.member.equals(member);
    }
}
