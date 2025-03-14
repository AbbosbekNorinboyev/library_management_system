package uz.pdp.library_management_system.response;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CategoryResponse {
    private String name;
    private String description;
    private Long createdBy;
    private LocalDateTime createdAt;
    private Long updatedBy;
    private LocalDateTime updatedAt;
}
