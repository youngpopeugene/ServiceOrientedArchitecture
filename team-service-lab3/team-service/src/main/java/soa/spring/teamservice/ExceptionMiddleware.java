package soa.spring.teamservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionMiddleware {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> serviceException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_XML)
                .body("<error><message>" +  ex.getMessage() + "</message></error>");
    }

    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<String> serviceException(ServiceException ex) {
        return ResponseEntity.status(HttpStatus.valueOf(ex.getCode()))
                .contentType(MediaType.APPLICATION_XML)
                .body("<error><message>" +  ex.getMessage() + "</message></error>");
    }
}
