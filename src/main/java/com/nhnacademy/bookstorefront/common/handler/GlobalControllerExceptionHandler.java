package com.nhnacademy.bookstorefront.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.bookstorefront.common.exception.LoginFailException;
import com.nhnacademy.bookstorefront.common.exception.NonMemberAccessFailException;
import com.nhnacademy.bookstorefront.common.exception.OauthMemberNotRegisteredException;
import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.dto.auth.AccessTokenReIssueRequestDto;
import com.nhnacademy.bookstorefront.main.dto.auth.AccessTokenReIssueResponseDto;
import com.nhnacademy.bookstorefront.main.dto.mypage.MyPageDto;
import com.nhnacademy.bookstorefront.main.service.CookieService;
import feign.FeignException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Base64;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerExceptionHandler {

    private final AuthenticationClient authenticationClient;
    private final CookieService cookieService;

    @ExceptionHandler(LoginFailException.class)
    public String handleLoginFailure(LoginFailException ex, RedirectAttributes redirectAttributes) {
        String message = ex.getMessage();
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/login";
    }

    @ExceptionHandler(OauthMemberNotRegisteredException.class)
    public String handleNotRegisteredOauthMember(OauthMemberNotRegisteredException ex, RedirectAttributes redirectAttributes) {
        String email = ex.getMessage();
        redirectAttributes.addFlashAttribute("message", "최초 회원가입이 필요한 서비스입니다.");
        return "redirect:/signup?email=" + email;
    }

    @ExceptionHandler(FeignException.Unauthorized.class)
    public String handleFeignUnauthorized(FeignException.Unauthorized e,  HttpServletResponse response, HttpServletRequest request) {
        String errorMessage = e.getMessage();
        if (errorMessage.contains("만료")) {
            try {
                // TODO: 재발급 요청
                //만료된 토큰에서 이메일 추출
                String email = "";
                String token = getTokenFromCookies(request);
                if(token != null) {
                    email = authenticationClient.getEmailFromToken("Bearer " + token).getBody();
                }

                ResponseEntity<AccessTokenReIssueResponseDto> responseEntity = authenticationClient.reIssueAccessToken(new AccessTokenReIssueRequestDto(email));
                String newToken = responseEntity.getBody().reIssuedAccessToken();

                cookieService.addCookie(response, "accessToken", newToken, 100000);

                return "redirect:/index";
            }catch (Exception ex){
                return "redirect:/login";
            }
        }
        // 나머지 재로그인 처리
        return "redirect:/login";
    }
    private String getTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    @ExceptionHandler(NonMemberAccessFailException.class)
    public String handleNonMemberAccessFail(NonMemberAccessFailException e,
                                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", e.getMessage());
        return "redirect:/non-member/order";
    }
}
