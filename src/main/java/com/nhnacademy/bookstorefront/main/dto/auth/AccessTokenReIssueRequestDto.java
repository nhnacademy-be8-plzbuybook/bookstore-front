package com.nhnacademy.bookstorefront.main.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AccessTokenReIssueRequestDto(@NotBlank @Email String email) {
}
