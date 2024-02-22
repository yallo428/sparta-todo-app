package org.example.spartatodoapp.service;

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

    public Member signup(CreateMemberDTO dto) {
        if(memberRepository.findByUsername(dto.getUserName()).isPresent()){
            throw new IllegalArgumentException("중복 회원입니다.");
        }

        String password = passwordEncoder.encode(dto.getPassword());

        return memberRepository.save(new Member(dto.getUserName(), password));
    }

    public String signin(CreateMemberDTO createMemberDTO) {
        Member member = memberRepository.findByUsername(createMemberDTO.getUserName()).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 없습니다."));

        member.passwordCheck(passwordEncoder, createMemberDTO.getPassword());

        return jwtUtil.createToken(member.getUsername());
    }

}


