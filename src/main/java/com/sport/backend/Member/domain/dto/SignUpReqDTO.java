package com.sport.backend.Member.domain.dto;

import com.sport.backend.Member.domain.entity.Member;
import com.sport.backend.Member.domain.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "회원가입 요청 DTO")
@Getter
@Setter
@NoArgsConstructor
public class SignUpReqDTO {

    @Schema(description = "사용자 아이디", example = "user123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하여야 합니다")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문자와 숫자만 사용 가능합니다")
    private String userId;

    @Schema(description = "비밀번호 (영문, 숫자, 특수문자 포함)", example = "password123@", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", 
             message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다")
    private String password;

    @Schema(description = "이름 (한글 또는 영문)", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하여야 합니다")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름은 한글 또는 영문만 사용 가능합니다")
    private String name;

    @Schema(description = "성별 (M: 남자, W: 여자)", example = "M", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "성별을 선택해주세요.")
    private Gender gender;

    @Schema(description = "생년월일 (YYYYMMDD 형식)", example = "19900101", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "생년월일을 입력해주세요.")
    @Pattern(regexp = "^\\d{8}$", message = "생년월일은 8자리 숫자여야 합니다 (예: 19900101)")
    private String birth;

    @Schema(description = "이메일 주소", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "유효한 이메일 주소를 입력해주세요.")
    private String email;

    @Schema(description = "전화번호 (11자리 숫자)", example = "01012345678", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^[0-9]{11}$",
            message = "핸드폰번호는 11자리 숫자여야 합니다")
    private String phone;

    public Member toEntity() {
        return Member.builder()
                .userId(this.userId)
                .password(this.password)
                .name(this.name)
                .gender(this.gender)
                .birthDate(this.birth)
                .email(this.email)
                .phoneNumber(this.phone)
                .build();
    }
}