package org.example.spartatodoapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.spartatodoapp.dto.CreateMemberDTO;
import org.example.spartatodoapp.jwt.JwtUtil;
import org.example.spartatodoapp.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody CreateMemberDTO createMemberDTO){
        memberService.signup(createMemberDTO);
        return ResponseEntity.ok("OK");
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CreateMemberDTO createMemberDTO){
        String token = memberService.signin(createMemberDTO);

        return ResponseEntity.ok().header(JwtUtil.AUTHORIZATION_HEADER, token).body("로그인 성공");
    }


}
