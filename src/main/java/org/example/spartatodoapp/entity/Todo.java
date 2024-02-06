package org.example.spartatodoapp.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Todo extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean complete;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    @JoinColumn(name = "todo_id")
    private List<Comment> comments = new ArrayList<>();


    public Todo(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.complete = false;
    }


    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }

    public void complete() {
        this.complete = true;
    }
}
