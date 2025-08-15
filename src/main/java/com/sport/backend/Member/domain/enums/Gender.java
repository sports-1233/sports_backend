package com.sport.backend.Member.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    M("남자"), W("여자");

    private String value;
}
