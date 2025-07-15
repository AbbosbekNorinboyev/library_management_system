package uz.pdp.library_management_system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.entity.AuthUser;
import uz.pdp.library_management_system.repository.AuthUserRepository;

@Component
@RequiredArgsConstructor
public class SessionId {

    private final AuthUserRepository authUserRepository;

    public Long getSessionId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return authUserRepository.findByUsername(username)
                .map(AuthUser::getId)
                .orElseThrow(() -> new RuntimeException("AuthUser not found!"));
    }
}
