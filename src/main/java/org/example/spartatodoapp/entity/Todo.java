package org.example.spartatodoapp.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Todo extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    private String title;
    private String content;
    private Boolean complete;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "todo")
    private List<Comment> comments = new ArrayList<>();

    public Todo(String title, String content, Boolean complete, Member member) {
        this.title = title;
        this.content = content;
        this.complete = complete;
        this.member = member;
        member.todos.add(this);
    }


    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", complete=" + complete +
                ", member=" + member +
                ", comments=" + comments +
                '}';
    }
}
