package org.example.spartatodoapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne
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
}
