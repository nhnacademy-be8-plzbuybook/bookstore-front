package com.nhnacademy.bookstorefront.common.exception;

public class OauthMemberNotRegisteredException extends RuntimeException{
    public OauthMemberNotRegisteredException(String email) {
        super(email);
    }
}
