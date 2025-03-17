package uz.pdp.library_management_system.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class BorrowingRequest {
    @NotBlank(message = "bookId can not be null or empty")
    private Long bookId;
    private LocalDate borrowDate; // kitobni ijaraga olingan sanasi
    private LocalDate dueDate; // kitobni qaytarish sanasi
    private LocalDate returnDate; // kitobni qaytarilgan sanasi
    private String status;
    private Long createdBy;
    private LocalDateTime createdAt;
    private Long updatedBy;
    private LocalDateTime updatedAt;
}
