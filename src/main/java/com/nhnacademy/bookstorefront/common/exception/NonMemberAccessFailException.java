package com.nhnacademy.bookstorefront.common.exception;

public class NonMemberAccessFailException extends RuntimeException {
    public NonMemberAccessFailException(String message) {
        super(message);
    }
}
