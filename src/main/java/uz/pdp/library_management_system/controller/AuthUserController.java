package uz.pdp.library_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import uz.pdp.library_management_system.config.CustomUserDetailsService;
import uz.pdp.library_management_system.dto.LoginDto;
import uz.pdp.library_management_system.dto.RegisterDto;
import uz.pdp.library_management_system.entity.AuthUser;
import uz.pdp.library_management_system.enums.Role;
import uz.pdp.library_management_system.exception.CustomException;
import uz.pdp.library_management_system.repository.AuthUserRepository;
import uz.pdp.library_management_system.util.JWTUtil;

import java.util.Optional;

import static uz.pdp.library_management_system.util.PasswordHasher.hashPassword;

@RestController
@RequestMapping("/api/auths")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3344")
public class AuthUserController {
    private final AuthUserRepository authUserRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto) {
        Optional<AuthUser> byUsername = authUserRepository.findByUsername(registerDto.getUsername());
        if (byUsername.isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        AuthUser authUser = AuthUser.builder()
                .username(registerDto.getUsername())
                .password(hashPassword(registerDto.getPassword()))
                .fullName(registerDto.getFullName())
                .phoneNumber(registerDto.getPhoneNumber())
                .role(Role.USER)
                .build();
        authUserRepository.save(authUser);
        return ResponseEntity.ok("AuthUser successfully register");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto loginDto) {
        AuthUser authUser = authUserRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "AuthUser not found by username: " + loginDto.getUsername()));
        if (authUser.getUsername() == null) {
            return ResponseEntity.ok().body("Username not found");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getUsername());
        String jwtGenerateToken = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(jwtGenerateToken);
    }
}
