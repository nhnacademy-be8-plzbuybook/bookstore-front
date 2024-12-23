package com.nhnacademy.bookstorefront.common.handler;

import com.nhnacademy.bookstorefront.common.exception.LoginFailException;
import com.nhnacademy.bookstorefront.common.exception.OauthMemberNotRegisteredException;
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
        return "redirect:/members/signup?email=" + email;
    }
}
