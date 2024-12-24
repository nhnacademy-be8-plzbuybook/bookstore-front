package com.nhnacademy.bookstorefront.main.dto.auth;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OauthLoginResponseDto(
        @NotNull
        Boolean isRegistered,
        @NotBlank
        String email,
        @Nullable
        String accessToken){
}
