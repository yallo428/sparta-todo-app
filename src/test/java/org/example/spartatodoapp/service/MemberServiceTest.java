package org.example.spartatodoapp.service;

import org.example.spartatodoapp.dto.CreateMemberDTO;
import org.example.spartatodoapp.entity.Member;
import org.example.spartatodoapp.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Rollback(value = false)
    void save() {
        CreateMemberDTO dto = new CreateMemberDTO("test", "1234");
        String password = passwordEncoder.encode(dto.getPassword());
        try {
            memberRepository.save(new Member(dto.getUserName(), password));
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void passwordCheck(){
        Member member = memberRepository.findByUsername("test123").orElseThrow();

        boolean matches = passwordEncoder.matches("1234", member.getPassword());

        System.out.println(matches);
    }

}