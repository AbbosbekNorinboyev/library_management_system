package uz.pdp.library_management_system.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RegisterCreateDTO {
    @NotBlank(message = "username can not be null or empty")
    private String username;
    @NotBlank(message = "password can not be null or empty")
    private String password;
    @NotBlank(message = "fullName can not be null or empty")
    private String fullName;
    @NotBlank(message = "phoneNumber can not be null or empty")
    private String phoneNumber;
}