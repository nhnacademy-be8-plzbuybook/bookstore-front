package com.nhnacademy.bookstorefront.main.dto.auth;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.logging.Filter;

public record OauthLoginResponseDto(
        @NotNull
        Boolean isRegistered,
        @NotBlank
        String email,
        @Nullable
        String accessToken,
        @NotBlank
        String memberStateName
){


}
