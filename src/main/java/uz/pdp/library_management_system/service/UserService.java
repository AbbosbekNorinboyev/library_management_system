package uz.pdp.library_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.library_management_system.dto.Response;

public interface UserService {
    Response getAllUsers(Pageable pageable);
}
