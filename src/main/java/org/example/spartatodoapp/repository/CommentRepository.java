package org.example.spartatodoapp.repository;

import org.example.spartatodoapp.entity.Comment;
import org.example.spartatodoapp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteByMember(Member member);
}
