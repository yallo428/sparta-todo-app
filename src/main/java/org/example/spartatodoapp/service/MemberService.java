package org.example.spartatodoapp.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.spartatodoapp.dto.CreateMemberDTO;
import org.example.spartatodoapp.entity.Member;
import org.example.spartatodoapp.jwt.JwtUtil;
import org.example.spartatodoapp.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(CreateMemberDTO dto) {
        String password = passwordEncoder.encode(dto.getPassword());
        try {
            memberRepository.save(new Member(dto.getUserName(), password));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String signin(CreateMemberDTO createMemberDTO) {
        Member member = memberRepository.findByUsername(createMemberDTO.getUserName()).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 없습니다."));
        if (passwordEncoder.matches(createMemberDTO.getPassword(), member.getPassword())) {
            return jwtUtil.createToken(member.getUsername());
        }
        throw new IllegalArgumentException("테스트");
    }


}
