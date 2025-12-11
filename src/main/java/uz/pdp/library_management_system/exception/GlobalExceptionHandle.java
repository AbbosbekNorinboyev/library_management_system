package uz.pdp.library_management_system.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.library_management_system.dto.Empty;
import uz.pdp.library_management_system.dto.ErrorResponse;
import uz.pdp.library_management_system.dto.Response;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandle {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream().map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage()).toList();
        StringBuilder stringBuilder = new StringBuilder();
        errors.forEach(s -> stringBuilder.append(s).append(System.lineSeparator()));
        String errorMessage = !stringBuilder.toString().isEmpty() ? stringBuilder.toString() : e.getMessage();

        ErrorResponse validationError = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())  // Bad request kodi
                .message(errorMessage)
                .build();

        var response = Response.builder()
                .success(false)
                .error(validationError)
                .data(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> handleInvalidFormatExceptions(InvalidFormatException ex) {
        String errorMessage = "Invalid format for field " + ex.getPath().get(0).getFieldName();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value()) // INTERNAL_SERVER_ERROR
                .message(errorMessage)
                .build();

        var response = Response.builder()
                .success(false)
                .error(errorResponse)
                .data(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralExceptions(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value()) // INTERNAL_SERVER_ERROR
                .message("Something wrong -> " + exception.getMessage())
                .build();

        var response = Response.builder()
                .success(false)
                .error(errorResponse)
                .data(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeExceptions(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value()) // INTERNAL_SERVER_ERROR
                .message("Something wrong -> " + exception.getMessage())
                .build();

        var response = Response.builder()
                .success(false)
                .error(errorResponse)
                .data(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();

        var response = Response.builder()
                .success(false)
                .error(errorResponse)
                .data(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<?> handleJsonParseException(JsonParseException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value()) // INTERNAL_SERVER_ERROR
                .message("Error while converting object to string: " + exception.getMessage())
                .build();

        var response = Response.builder()
                .success(false)
                .error(errorResponse)
                .data(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleGeneralCustomExceptions(CustomException customException) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(customException.getCode())
                .message(customException.getMessage())
                .build();

        var response = Response.builder()
                .success(false)
                .error(errorResponse)
                .data(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
