package sit.int202.springmodule2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import sit.int202.springmodule2.exceptions.MyErrorResponse;
import sit.int202.springmodule2.exceptions.CustomerNotFoundException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity handleCustomerNotFound(CustomerNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MyErrorResponse> handleRequestBodyValidation(MethodArgumentNotValidException e) {
        MyErrorResponse error = MyErrorResponse
                    .builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMessage("Validation errors")
                    .build();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<MyErrorResponse> handleControllerValidation(HandlerMethodValidationException e) {
        MyErrorResponse errorResponse = MyErrorResponse
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errorMessage(e.getAllErrors().get(0).getDefaultMessage())
                .build();

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
