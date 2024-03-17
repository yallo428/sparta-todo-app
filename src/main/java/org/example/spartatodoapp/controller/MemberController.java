package org.example.spartatodoapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.spartatodoapp.dto.MemberFormDTO;
import org.example.spartatodoapp.util.JwtUtil;
import org.example.spartatodoapp.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @RequestBody @Validated MemberFormDTO memberFormDTO
    ){
        memberService.signup(memberFormDTO);
        return ResponseEntity.ok("OK");
    }



}
