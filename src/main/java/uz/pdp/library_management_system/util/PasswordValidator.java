package uz.pdp.library_management_system.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordValidator {
    private PasswordValidator() {
    }

    public static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static boolean validatePassword(String rawPassword, String hasPassword) {
        return encoder.matches(rawPassword, hasPassword);
    }
}
