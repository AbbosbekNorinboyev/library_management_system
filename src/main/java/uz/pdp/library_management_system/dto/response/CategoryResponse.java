package uz.pdp.library_management_system.dto.response;

import lombok.*;
import uz.pdp.library_management_system.enums.Status;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private Long libraryId;
    private Status status;
    private Long createdBy;
    private LocalDateTime createdAt;
    private Long updatedBy;
    private LocalDateTime updatedAt;
}
