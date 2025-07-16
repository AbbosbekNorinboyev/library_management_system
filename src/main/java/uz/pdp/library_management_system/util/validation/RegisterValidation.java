package uz.pdp.library_management_system.util.validation;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.dto.ErrorResponse;
import uz.pdp.library_management_system.dto.RegisterCreateDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegisterValidation {
    public List<ErrorResponse> validate(RegisterCreateDTO registerCreateDTO) {
        List<ErrorResponse> errors = new ArrayList<>();
        if (!registerCreateDTO.getUsername().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            errors.add(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "username is invalid"));
        }
        if (!registerCreateDTO.getPhoneNumber().matches("^\\+998\\d{9}$")) {
            errors.add(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "phoneNUmber is invalid"));
        }
        return errors;
    }
}
