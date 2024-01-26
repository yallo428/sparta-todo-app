package org.example.spartatodoapp.repository;

import org.example.spartatodoapp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
