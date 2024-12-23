package com.nhnacademy.bookstorefront.common.handler;

import com.nhnacademy.bookstorefront.common.exception.LoginFailException;
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
}
