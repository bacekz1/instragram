package com.s14ittalents.insta.exception;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class ExceptionDTO {
    private String message;
    private int status;
    private LocalDateTime time;
}
