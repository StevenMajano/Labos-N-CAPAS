package org.example.laboratorio3.models.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
@Builder
public class GeneralResponse<T> {
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String uri;
    private T data;
}
