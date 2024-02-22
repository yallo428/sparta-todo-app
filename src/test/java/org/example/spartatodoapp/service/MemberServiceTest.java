package org.example.spartatodoapp.service;

import org.assertj.core.api.Assertions;
import org.example.spartatodoapp.dto.CreateMemberDTO;
import org.example.spartatodoapp.entity.Member;
import org.example.spartatodoapp.jwt.JwtUtil;
import org.example.spartatodoapp.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    JwtUtil jwtUtil;

    MemberService memberService;
    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository, passwordEncoder, jwtUtil);
    }

    @Test
    void 회원_저장() {
        CreateMemberDTO dto = new CreateMemberDTO();
        dto.setUserName("test");
        dto.setPassword("1234");
        Member member = new Member("test", "1234");

        memberService.signup(dto);
        given(memberRepository.findByUsername(member.getUsername())).willReturn(Optional.of(member));

        Member findMember = memberRepository.findByUsername(member.getUsername()).orElseThrow();

        assertEquals(dto.getUserName(), findMember.getUsername());
    }

    @Test
    void 중복_회원(){
        CreateMemberDTO dto = new CreateMemberDTO();
        dto.setUserName("test");
        dto.setPassword("1234");
        Member member = new Member("test", "1234");

        given(memberRepository.findByUsername(member.getUsername())).willReturn(Optional.of(member));
        Assertions.assertThatThrownBy( () ->
                memberService.signup(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복 회원입니다.");
    }


    @Test
    void 로그인(){
        CreateMemberDTO dto = new CreateMemberDTO();
        dto.setUserName("test");
        dto.setPassword("1234");
        String password = passwordEncoder.encode(dto.getPassword());
        Member member = new Member("test", password);

        given(memberRepository.findByUsername(member.getUsername())).willReturn(Optional.of(member));
        passwordEncoder.matches("1234", password);
        given(jwtUtil.createToken("test")).willReturn("token");

        String token = memberService.signin(dto);

        assertEquals(token, "token");
    }

    @Test
    void 패스워드_불일치(){
        CreateMemberDTO dto = new CreateMemberDTO();
        dto.setUserName("test");
        dto.setPassword("1234");
        String password = passwordEncoder.encode(dto.getPassword());

        Member member = new Member("test", password);

        Assertions.assertThatThrownBy(()->
                        member.passwordCheck(passwordEncoder, "12345"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("패스워드가 다릅니다.");
    }

}