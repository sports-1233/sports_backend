package com.sport.backend.Member.service;

import com.sport.backend.Member.domain.dto.SignUpRequest;
import com.sport.backend.Member.domain.entity.Member;
import com.sport.backend.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public int signUp(SignUpRequest request) {

        int result = 0;

        validateDuplicateUser(request);
        validateBirthDate(request.getBirth());
        
        Member member = request.toEntity();
        memberRepository.save(member);
        result++;

        return result;
    }

    private void validateDuplicateUser(SignUpRequest request) {
        if (memberRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다");
        }
        
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
        }
        
        if (memberRepository.existsByPhoneNumber(request.getPhone())) {
            throw new IllegalArgumentException("이미 존재하는 핸드폰번호입니다");
        }
    }

    private void validateBirthDate(String birthDate) {
        try {
            LocalDate date = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
            LocalDate now = LocalDate.now();
            
            if (date.isAfter(now)) {
                throw new IllegalArgumentException("생년월일은 현재 날짜보다 이전이어야 합니다");
            }
            
            if (date.isBefore(now.minusYears(100))) {
                throw new IllegalArgumentException("올바르지 않은 생년월일입니다");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바르지 않은 생년월일 형식입니다");
        }
    }

}