package uz.pdp.library_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.library_management_system.dto.ErrorDTO;
import uz.pdp.library_management_system.dto.RegisterCreateDTO;
import uz.pdp.library_management_system.entity.AuthUser;
import uz.pdp.library_management_system.enums.Role;
import uz.pdp.library_management_system.repository.AuthUserRepository;
import uz.pdp.library_management_system.validation.RegisterValidation;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3344")
public class AuthUserController {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterValidation registerValidation;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterCreateDTO registerCreateDTO) {
        System.out.println("registerCreateDTO = " + registerCreateDTO);
        Optional<AuthUser> byUsername = authUserRepository.findByUsername(registerCreateDTO.getUsername());
        if (byUsername.isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        List<ErrorDTO> errors = registerValidation.validate(registerCreateDTO);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body("Validation error");
        }
        AuthUser authUser = AuthUser.builder()
                .username(registerCreateDTO.getUsername())
                .password(passwordEncoder.encode(registerCreateDTO.getPassword()))
                .fullName(registerCreateDTO.getFullName())
                .phoneNumber(registerCreateDTO.getPhoneNumber())
                .role(Role.USER)
                .build();
        System.out.println("authUser register = " + authUser);
        authUserRepository.save(authUser);
        return ResponseEntity.ok("AuthUser successfully register");
    }
}
