package com.sport.backend.member.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 요청 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginReqDTO {

    @Schema(description = "사용자 아이디", example = "user123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @Schema(description = "비밀번호", example = "password123@", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}