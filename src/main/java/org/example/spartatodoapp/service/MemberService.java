package org.example.spartatodoapp.service;

import lombok.RequiredArgsConstructor;
import org.example.spartatodoapp.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
}
