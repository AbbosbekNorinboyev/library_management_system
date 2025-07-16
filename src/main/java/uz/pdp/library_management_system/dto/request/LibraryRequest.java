package uz.pdp.library_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class LibraryRequest {
    @NotBlank(message = "name can not be null or empty")
    private String name;
    @NotBlank(message = "address can not be null or empty")
    private String address;
    @NotBlank(message = "phone can not be null or empty")
    private String phone;
    @NotBlank(message = "email can not be null or empty")
    private String email;
    private Long createdBy;
    private LocalDateTime createdAt;
    private Long updatedBy;
    private LocalDateTime updatedAt;
}
