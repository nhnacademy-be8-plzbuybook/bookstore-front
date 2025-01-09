package com.nhnacademy.bookstorefront.common.handler;

import com.nhnacademy.bookstorefront.common.exception.LoginFailException;
import com.nhnacademy.bookstorefront.common.exception.NonMemberAccessFailException;
import com.nhnacademy.bookstorefront.common.exception.OauthMemberNotRegisteredException;
import feign.FeignException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

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
    public String handleFeignUnauthorized(FeignException.Unauthorized e) {
        String errorMessage = e.getMessage();
        if (errorMessage.contains("만료")) {
            // TODO: 재발급 요청
        }
        // 나머지 재로그인 처리
        return "redirect:/login";
    }

    @ExceptionHandler(NonMemberAccessFailException.class)
    public String handleNonMemberAccessFail(NonMemberAccessFailException e,
                                            Model model) {
        return "redirect:/non-member/order";

    }
}
