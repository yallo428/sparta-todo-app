package org.example.spartatodoapp.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.spartatodoapp.dto.CommentDetailDTO;
import org.example.spartatodoapp.dto.SelectedTodoDTO;
import org.example.spartatodoapp.dto.TodoForm;
import org.example.spartatodoapp.dto.TodoDetailDTO;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Todo extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "todo_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean complete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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

    public boolean isNotUserName(String username){
        return !member.isNotUserName(username);
    }

    public TodoForm createTodoForm(){
        return TodoForm.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }

    public SelectedTodoDTO createSelectTodoDTO(){
        List<CommentDetailDTO> commentDTOs = comments
                .stream()
                .map(Comment::createCommentDto)
                .toList();

        return SelectedTodoDTO.builder()
                .userName(this.getMember().getUsername())
                .title(this.title)
                .content(this.content)
                .createAt(this.getCreateAt())
                .comments(commentDTOs)
                .build();
    }

    public TodoDetailDTO createTodoDetailDTO(){
        return TodoDetailDTO.builder()
                .userName(this.member.getUsername())
                .title(this.title)
                .createAt(this.getCreateAt())
                .complete(this.complete).build();
    }
}
