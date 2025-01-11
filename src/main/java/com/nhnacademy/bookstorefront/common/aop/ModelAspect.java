package com.nhnacademy.bookstorefront.common.aop;

import com.nhnacademy.bookstorefront.main.client.AuthenticationClient;
import com.nhnacademy.bookstorefront.main.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Aspect
@Component
public class ModelAspect {

    private final AuthenticationService authenticationService;
    private final AuthenticationClient authenticationClient;

    public ModelAspect(AuthenticationService authenticationService, AuthenticationClient authenticationClient) {
        this.authenticationService = authenticationService;
        this.authenticationClient = authenticationClient;
    }


    @Pointcut("execution(* com.nhnacademy.bookstorefront..*Controller.*(..))")
    // Model을 인자로 받는 모든 컨트롤러 메서드
    public void modelPointcut() {
    }

    @Around("modelPointcut()")
    public Object addAttributesToModel(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Model model = null;
        HttpServletRequest request = null;

        // Model 객체 찾기
        for (Object arg : args) {
            if (arg instanceof Model) {
                model = (Model) arg;
            }
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
            }
        }

        if (model != null && request != null) {
            boolean isLoggedIn = authenticationService.isLoggedIn(request);
            String role = null;

            if (isLoggedIn) {
                String token = getTokenFromCookies(request);
                if (token != null) {
                    role = authenticationClient.getRoleFromToken("Bearer " + token).getBody();
                }

            }
            model.addAttribute("isLoggedIn", isLoggedIn);
            model.addAttribute("role", role);
        }
        return joinPoint.proceed();
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
}
