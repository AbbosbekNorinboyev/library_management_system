package uz.pdp.library_management_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.library_management_system.enums.Status;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private Integer totalPages;
    private Long availableCopies;
    private Long categoryId;
    private Status status;
    private Long createdBy;
    private LocalDateTime createdAt;
    private Long updatedBy;
    private LocalDateTime updatedAt;
}
