package uz.pdp.library_management_system.validation;

import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.dto.ErrorDTO;
import uz.pdp.library_management_system.dto.RegisterCreateDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegisterValidation {
    public List<ErrorDTO> validate(RegisterCreateDTO registerCreateDTO) {
        List<ErrorDTO> errors = new ArrayList<>();
        if (!registerCreateDTO.getUsername().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            errors.add(new ErrorDTO("username", "username is invalid"));
        }
        if (!registerCreateDTO.getPhoneNumber().matches("^\\+998\\d{9}$")) {
            errors.add(new ErrorDTO("phoneNumber", "phoneNUmber is invalid"));
        }
        return errors;
    }
}
