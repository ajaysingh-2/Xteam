package com.xsworld.userservice.exceptioncontroller;

import com.xsworld.userservice.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<com.gmagica.user.service.cloudberryuserservice.exceptioncontroller.ExceptionMessage> handleUserException(UserException ex) {
        log.error("User Exception: {}", ex.getMessage());
        com.gmagica.user.service.cloudberryuserservice.exceptioncontroller.ExceptionMessage exceptionMessage = com.gmagica.user.service.cloudberryuserservice.exceptioncontroller.ExceptionMessage.builder()
                .message(ex.getMessage())
                .time(LocalDateTime.now())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.badRequest().body(exceptionMessage);
    }
}
