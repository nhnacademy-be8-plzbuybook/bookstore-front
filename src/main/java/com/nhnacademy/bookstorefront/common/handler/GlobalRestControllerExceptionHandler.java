package com.nhnacademy.bookstorefront.common.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.bookstorefront.common.exception.ConflictException;
import com.nhnacademy.bookstorefront.common.exception.ErrorResponseDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalRestControllerExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleConflictException(ConflictException e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDto);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignException(FeignException e) throws JsonProcessingException {
        String responseBody = e.contentUTF8();
        JsonNode jsonResponse = objectMapper.readTree(responseBody);
        String errorMessage = jsonResponse.get("message").asText();
        
        return ResponseEntity.status(e.status()).body(errorMessage);
    }
}
