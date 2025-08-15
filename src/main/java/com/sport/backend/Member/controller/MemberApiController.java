package com.sport.backend.Member.controller;

import com.sport.backend.Member.domain.dto.SignUpRequest;
import com.sport.backend.Member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<Integer> signUp(@Valid @RequestBody SignUpRequest request) {
            int result = memberService.signUp(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}