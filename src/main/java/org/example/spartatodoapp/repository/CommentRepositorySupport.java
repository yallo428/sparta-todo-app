package org.example.spartatodoapp.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.spartatodoapp.dto.CommentDetailDTO;
import org.example.spartatodoapp.entity.QMember;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.example.spartatodoapp.entity.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;


    
}
