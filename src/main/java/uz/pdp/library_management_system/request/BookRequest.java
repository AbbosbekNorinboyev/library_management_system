package uz.pdp.library_management_system.request;

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
public class BookRequest {
    @NotBlank(message = "title can not be null or empty")
    private String title;
    @NotBlank(message = "author can not be null or empty")
    private String author;
    private Integer totalPages;
    private Long availableCopies;
    @NotBlank(message = "categoryId can not be null or empty")
    private Long categoryId;
    private Long createdBy;
    private LocalDateTime createdAt;
    private Long updatedBy;
    private LocalDateTime updatedAt;
}
