package uz.pdp.library_management_system.validation;

import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.dto.ErrorDTO;
import uz.pdp.library_management_system.request.BookRequest;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookValidation {
    public List<ErrorDTO> validate(BookRequest bookRequest) {
        List<ErrorDTO> errors = new ArrayList<>();
        if (bookRequest.getAvailableCopies() <= 0) {
            errors.add(new ErrorDTO("availableCopies", "availableCopies can not be negative or zero"));
        }
        if (bookRequest.getTotalPages() <= 0) {
            errors.add(new ErrorDTO("totalPages", "totalPages can not be negative or zero"));
        }
        return errors;
    }
}
