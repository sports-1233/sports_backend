package com.sport.backend.global.jwt.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDTO {

    private String accessToken;
    private String refreshToken;
}
