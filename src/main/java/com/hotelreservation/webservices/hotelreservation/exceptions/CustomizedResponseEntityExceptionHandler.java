package com.hotelreservation.webservices.hotelreservation.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HotelNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleHotelNotFoundException(Exception ex, WebRequest request) throws Exception {

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Hotel not found", request.getDescription(false));

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleRoomNotFoundException(Exception ex, WebRequest request) throws Exception {

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Room Not found", request.getDescription(false));

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                ex.getFieldError().getDefaultMessage(), request.getDescription(false));

        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);

    }


}
