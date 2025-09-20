package com.sport.backend.global.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CookieUtil {

    private final static int MAX_AGE = 60 * 60; // 1시간

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> name.equals(cookie.getName()))
                    .findFirst();
        }
        return Optional.empty();
    }

    public static void addCookie(HttpServletResponse response, String name, String val) {
        Cookie cookie = new Cookie(name, val);

        cookie.setHttpOnly(true);
        cookie.setSecure(false); // HTTPS에서만 전송 (개발환경에서는 false)
        cookie.setPath("/");
        cookie.setMaxAge(MAX_AGE);
//        cookie.setAttribute("SameSite", "None") // 크로스 사이트 요청에서도 쿠키 전송 허용, third party 혹은 iframe, secure(true)랑 같이 사용해야함
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie cookie = getCookie(request, name).orElseThrow(() -> new NoSuchElementException("쿠키가 존재하지 않습니다."));

        cookie.setValue("");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(false);
//        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);
    }
}
