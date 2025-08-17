package com.sport.backend.Member.controller;

import com.sport.backend.Member.domain.dto.SignUpReqDTO;
import com.sport.backend.Member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 관리", description = "회원 가입, 로그인 등 회원 관리 API")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @Operation(
            summary = "회원 가입",
            description = "필수 값 (아이디, 비밀번호, 이름, 성별, 생년월일, 이메일, 전화번호)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "회원가입 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "integer", description = "성공한 쿼리 수")
                    )
            )
    })
    @PostMapping("/signup")
    public ResponseEntity<Integer> signUp(
            @Parameter(description = "회원가입 요청 정보", required = true)
            @Valid @RequestBody SignUpReqDTO request) {
            int result = memberService.signUp(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}