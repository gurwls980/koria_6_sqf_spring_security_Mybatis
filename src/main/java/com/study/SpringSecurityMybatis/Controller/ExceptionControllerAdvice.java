package com.study.SpringSecurityMybatis.Controller;

import com.study.SpringSecurityMybatis.exception.SignupException;
import com.study.SpringSecurityMybatis.exception.ValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class ExceptionControllerAdvice {

    @ExceptionHandler(ValidException.class)
    public ResponseEntity<?> validException(ValidException e) {
        return ResponseEntity.badRequest().body(e.getFieldErrors());        // badRequest:404에러
    }

    @ExceptionHandler(SignupException.class)
    public ResponseEntity<?> signupException(SignupException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());   // internalServerError:505에러
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authticationException(AuthenticationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
