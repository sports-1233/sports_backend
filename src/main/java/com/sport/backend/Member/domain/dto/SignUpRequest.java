package com.sport.backend.Member.domain.dto;

import com.sport.backend.Member.domain.entity.Member;
import com.sport.backend.Member.domain.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하여야 합니다")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문자와 숫자만 사용 가능합니다")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", 
             message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하여야 합니다")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "이름은 한글 또는 영문만 사용 가능합니다")
    private String name;

    @NotNull(message = "성별을 선택해주세요.")
    private Gender gender;

    @NotBlank(message = "생년월일을 입력해주세요.")
    @Pattern(regexp = "^\\d{8}$", message = "생년월일은 8자리 숫자여야 합니다 (예: 19900101)")
    private String birth;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "유효한 이메일 주소를 입력해주세요.")
    private String email;

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