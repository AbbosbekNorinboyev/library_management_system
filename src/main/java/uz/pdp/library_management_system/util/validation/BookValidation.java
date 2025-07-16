package uz.pdp.library_management_system.util.validation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.dto.ErrorResponse;
import uz.pdp.library_management_system.dto.request.BookRequest;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookValidation {
    public List<ErrorResponse> validate(BookRequest bookRequest) {
        List<ErrorResponse> errors = new ArrayList<>();
        if (bookRequest.getAvailableCopies() <= 0) {
            errors.add(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "availableCopies can not be negative or zero"));
        }
        if (bookRequest.getTotalPages() <= 0) {
            errors.add(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "totalPages can not be negative or zero"));
        }
        return errors;
    }
}
