package com.nhnacademy.bookstorefront.main.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLastLoginRequestDto {
    private String email;
    private LocalDateTime lastLogin;
}
