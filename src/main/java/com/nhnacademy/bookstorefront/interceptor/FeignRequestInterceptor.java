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
        // 요청에서 쿠키 가져오기
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            // 쿠키에서 accessToken을 찾기
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    log.debug("accessToken cookie: {}", cookie.getValue());

                    // Authorization 헤더에 "Bearer {accessToken}" 형식으로 추가
                    template.header("Authorization", "Bearer " + cookie.getValue());
                }
            }
        }
    }
}
