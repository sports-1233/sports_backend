package com.sport.backend.Member.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "성별 구분")
@Getter
@AllArgsConstructor
public enum Gender {
    @Schema(description = "남자")
    M("남자"), 
    
    @Schema(description = "여자")
    W("여자");

    @Schema(description = "성별 값")
    private String value;
}
