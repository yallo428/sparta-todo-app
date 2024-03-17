package org.example.spartatodoapp.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.spartatodoapp.dto.MemberFormDTO;
import org.example.spartatodoapp.entity.Member;
import org.example.spartatodoapp.util.JwtUtil;
import org.example.spartatodoapp.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Member signup(MemberFormDTO dto) {
        if(memberRepository.findByUsername(dto.getUserName()).isPresent()){
            throw new EntityExistsException("중복 회원입니다.");
        }

        String password = passwordEncoder.encode(dto.getPassword());

        return memberRepository.save(new Member(dto.getUserName(), password));
    }

    public String signin(MemberFormDTO memberFormDTO) {
        Member member = memberRepository.findByUsername(memberFormDTO.getUserName()).orElseThrow(() ->
                new EntityNotFoundException("해당 유저가 없습니다."));

        member.passwordCheck(passwordEncoder, memberFormDTO.getPassword());

        return jwtUtil.createToken(member.getUsername());
    }

}


