package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.property.PaycoOauthProperties;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RequiredArgsConstructor
@Service
public class PaycoOauthService {
    private final PaycoOauthProperties properties;

    public void redirectToOauthLoginPage(HttpServletResponse response) {
        URI uri = UriComponentsBuilder
                .fromUriString(properties.getCodeUrl())
                .queryParam("client_id", properties.getClientId())
                .queryParam("response_type", properties.getResponseType())
                .queryParam("redirect_uri", properties.getRedirectUrl())
                .queryParam("serviceProviderCode", "FRIENDS")
                .queryParam("userLocale", "ko_KR")
                .encode()
                .build()
                .toUri();
        try {
            response.sendRedirect(uri.toString());
        } catch (IOException e) {
            throw new RuntimeException("Oauth2 로그인 페이지 리다이렉트 실패", e);
        }
    }
}
