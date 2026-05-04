package com.revobank.accounts.core.exceptions;
import com.revobank.accounts.core.accounts.AccountNotFoundException;
import com.revobank.accounts.core.transfers.NegativeBalanceException;
import com.revobank.accounts.core.transfers.NotExistPaymentTypeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.FieldError;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponseWrapper> handleBadRequest(AccountNotFoundException ex) {
        ErrorResponseWrapper error = new ErrorResponseWrapper(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NegativeBalanceException.class)
    public ResponseEntity<ErrorResponseWrapper> handleBadRequest(NegativeBalanceException ex) {
        ErrorResponseWrapper error = new ErrorResponseWrapper(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistPaymentTypeException.class)
    public ResponseEntity<ErrorResponseWrapper> handleBadRequest(NotExistPaymentTypeException ex) {
        ErrorResponseWrapper error = new ErrorResponseWrapper(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseWrapper> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponseWrapper errorResponse = new ErrorResponseWrapper(
                HttpStatus.BAD_REQUEST.value(),
                " Failed Validation",
                System.currentTimeMillis(),
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseWrapper> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Database error occurred";
        Map<String, String> errors = new HashMap<>();

        if (ex.getMessage() != null && ex.getMessage().contains("accounts_iban_key")) {
            message = "Failed Validation";
            errors.put("iban", "IBAN already exists");
        }

        ErrorResponseWrapper errorResponse = new ErrorResponseWrapper(
                HttpStatus.CONFLICT.value(),
                message,
                System.currentTimeMillis(),
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}