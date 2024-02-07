package com.gmagica.user.service.cloudberryuserservice.exceptioncontroller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionMessage {

    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime time;
}