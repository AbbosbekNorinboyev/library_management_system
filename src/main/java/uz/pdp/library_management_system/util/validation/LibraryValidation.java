package uz.pdp.library_management_system.util.validation;

import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.dto.ErrorDTO;
import uz.pdp.library_management_system.request.LibraryRequest;

import java.util.ArrayList;
import java.util.List;

@Component
public class LibraryValidation {
    public List<ErrorDTO> validate(LibraryRequest libraryRequest) {
        List<ErrorDTO> errors = new ArrayList<>();
        if (!libraryRequest.getPhone().matches("^\\+998\\d{9}$")) {
            errors.add(new ErrorDTO("phone", "phone number is invalid"));
        }
        if (!libraryRequest.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            errors.add(new ErrorDTO("email", "email is invalid"));
        }
        return errors;
    }
}
