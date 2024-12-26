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
public class FeignRequestInterceptor implements RequestInterceptor {

    private final HttpServletRequest request;

    public FeignRequestInterceptor(HttpServletRequest request) {
        this.request = request;
    }


    @Override
    public void apply(RequestTemplate template) {
        // URL이 "/api/members/my"로 시작하면 Authorization 헤더 추가
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
        //이런식으로 access 토큰이 필요한 것은 추가해주는 방법
    }
}