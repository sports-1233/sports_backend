package com.sport.backend.member.service;

import com.sport.backend.member.domain.dto.LoginReqDTO;
import com.sport.backend.member.domain.dto.SignUpReqDTO;
import com.sport.backend.member.domain.entity.Member;
import com.sport.backend.member.repository.MemberRepository;
import com.sport.backend.global.jwt.TokenProvider;
import com.sport.backend.global.jwt.dto.TokenDTO;
import com.sport.backend.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public int signUp(SignUpReqDTO request) {

        int result = 0;

        validateDuplicateUser(request);
        validateBirthDate(request.getBirth());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.updatePassword(encodedPassword);

        Member member = request.toEntity();


        memberRepository.save(member);
        result++;

        return result;
    }

    private void validateDuplicateUser(SignUpReqDTO request) {
        if (memberRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다");
        }
        
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다");
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

    @Transactional
    public void login(LoginReqDTO request, HttpServletResponse response) {
        Member member = memberRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        TokenDTO token = tokenProvider.createToken(member.getUserId());

        CookieUtil.addCookie(response, "at", token.getAccessToken());
        CookieUtil.addCookie(response, "rt", token.getRefreshToken());

    }

}