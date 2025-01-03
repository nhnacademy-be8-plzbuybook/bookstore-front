package com.nhnacademy.bookstorefront.main.dto.auth;

public record LoginResponseDto(
        String accessToken,
        String memberStateName,
        String redirectUrl,
        String role
){

}
