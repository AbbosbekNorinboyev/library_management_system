package uz.pdp.library_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.library_management_system.dto.ErrorDTO;
import uz.pdp.library_management_system.dto.LoginCreateDTO;
import uz.pdp.library_management_system.dto.RegisterCreateDTO;
import uz.pdp.library_management_system.entity.AuthUser;
import uz.pdp.library_management_system.enums.Role;
import uz.pdp.library_management_system.exception.CustomException;
import uz.pdp.library_management_system.repository.AuthUserRepository;
import uz.pdp.library_management_system.config.CustomUserDetailsService;
import uz.pdp.library_management_system.util.JWTUtil;
import uz.pdp.library_management_system.util.validation.RegisterValidation;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auths")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3344")
public class AuthUserController {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisterValidation registerValidation;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterCreateDTO registerCreateDTO) {
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
        authUserRepository.save(authUser);
        return ResponseEntity.ok("AuthUser successfully register");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginCreateDTO loginCreateDTO) {
        AuthUser authUser = authUserRepository.findByUsername(loginCreateDTO.getUsername())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "AuthUser not found by username: " + loginCreateDTO.getUsername()));
        if (authUser.getUsername() == null) {
            return ResponseEntity.ok().body("Username not found");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginCreateDTO.getUsername(), loginCreateDTO.getPassword())
        );
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginCreateDTO.getUsername());
        String jwtGenerateToken = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(jwtGenerateToken);
    }
}
