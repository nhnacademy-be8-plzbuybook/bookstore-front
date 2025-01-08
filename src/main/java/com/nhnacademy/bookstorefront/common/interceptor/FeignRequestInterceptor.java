package com.nhnacademy.bookstorefront.common.interceptor;

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
}
