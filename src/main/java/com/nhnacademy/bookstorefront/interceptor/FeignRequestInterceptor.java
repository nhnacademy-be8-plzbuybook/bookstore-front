package com.nhnacademy.bookstorefront.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
//feign cleint의 요청에 Authorization헤더를 추가하는 역할을 한다
//쿠키에서 access Token을 찾아서 jwt형식으로 헤더에 추가한다
//쿠키에 access Token이 있을 때만 작동
public class FeignRequestInterceptor implements RequestInterceptor {

    private final HttpServletRequest request;

    public FeignRequestInterceptor(HttpServletRequest request) {
        this.request = request;
    }


    @Override
    public void apply(RequestTemplate template) {
        // URL이 "/api/members/my"로 시작하면 Authorization 헤더 추가
        //이런식으로 access 토큰이 있더라도 제한을 둘 수 있다.
        if (template.url().startsWith("/api/members/my")) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("accessToken".equals(cookie.getName())) {
                        log.debug("Authorization 헤더가 추가된 요청의 URL: {}", template.url());
                        //access 토큰은 헤더가 없으므로 특정 요청에 대해 추가해서 header에 저장한다
                        template.header("Authorization", "Bearer " + cookie.getValue());
                    }
                }
            }
        }
        // 쿠키에 accessToken이 없거나 유효하지 않으면 다른 동작을 수행
//        if (토큰이 없을때) {
//            log.warn("Access Token이 쿠키에 존재하지 않습니다.");
//            // 예시: 로그인 페이지로 리디렉션 (Feign에서는 직접 리디렉션을 못 하지만, 다른 방법을 고려할 수 있음)
//            // 예: 예외를 던져서 처리하거나 다른 로직으로 전환할 수 있음
//        }
        //이런식으로 access 토큰이 필요한 것은 추가해주는 방법
    }
}