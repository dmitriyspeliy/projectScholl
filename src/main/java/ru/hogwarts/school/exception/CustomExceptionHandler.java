package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ElemNotFound.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(ElemNotFound ex) {
        String incorrectRequest = "Такого элемента нет";
        ErrorResponse error = new ErrorResponse(incorrectRequest, returnList(ex));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException(MethodArgumentNotValidException ex) {
        String badRequest = "Какие-то данные были введены неправильно";
        ErrorResponse error = new ErrorResponse(badRequest, Collections.singletonList(ex.getLocalizedMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IFElementExist.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException(IFElementExist ex) {
        String badRequest = "Элемент уже есть в базе";
        ErrorResponse error = new ErrorResponse(badRequest, returnList(ex));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private List<String> returnList(RuntimeException runtimeException) {
        return List.of(runtimeException.getLocalizedMessage());
    }

}
