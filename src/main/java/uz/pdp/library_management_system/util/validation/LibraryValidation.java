package uz.pdp.library_management_system.util.validation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.dto.ErrorResponse;
import uz.pdp.library_management_system.dto.request.LibraryRequest;

import java.util.ArrayList;
import java.util.List;

@Component
public class LibraryValidation {
    public List<ErrorResponse> validate(LibraryRequest libraryRequest) {
        List<ErrorResponse> errors = new ArrayList<>();
        if (!libraryRequest.getPhone().matches("^\\+998\\d{9}$")) {
            errors.add(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "phone number is invalid"));
        }
        if (!libraryRequest.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            errors.add(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "email is invalid"));
        }
        return errors;
    }
}
