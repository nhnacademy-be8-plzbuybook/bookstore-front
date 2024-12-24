package com.nhnacademy.bookstorefront.main.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OauthResponseDto (
        @NotNull
        Boolean isRegistered,
        @NotBlank
        String email,
        @Nullable
        MemberDto memberDto) {
}

