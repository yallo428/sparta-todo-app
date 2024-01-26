package org.example.spartatodoapp.repository;

import org.example.spartatodoapp.entity.Member;
import org.example.spartatodoapp.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findAllByMember(Member member);
}
