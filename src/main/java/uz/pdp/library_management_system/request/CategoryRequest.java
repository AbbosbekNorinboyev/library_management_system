package uz.pdp.library_management_system.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CategoryRequest {
    @NotBlank(message = "name can not be null or empty")
    private String name;
    private String description;
    @NotNull(message = "createdBy can not null or empty")
    private Long createdBy;
    private LocalDateTime createdAt;
    @NotNull(message = "updatedBy can not null or empty")
    private Long updatedBy;
    private LocalDateTime updatedAt;
}
