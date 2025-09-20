package com.sport.backend.member.controller;

import com.sport.backend.member.domain.dto.LoginReqDTO;
import com.sport.backend.member.domain.dto.SignUpReqDTO;
import com.sport.backend.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 관리", description = "회원 가입, 로그인 등 회원 관리 API")
@RestController
@RequestMapping("/v1/member")
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

    @Operation(
            summary = "로그인",
            description = "아이디와 비밀번호로 로그인 (JWT 토큰이 쿠키로 설정됩니다)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 로그인 정보"
            )
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Parameter(description = "로그인 요청 정보", required = true)
            @Valid @RequestBody LoginReqDTO request,
            HttpServletResponse response) {
        memberService.login(request, response);

        return ResponseEntity.status(HttpStatus.OK).body("로그인 성공");
    }
}