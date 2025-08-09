package uz.pdp.library_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.dto.request.CategoryRequest;
import uz.pdp.library_management_system.dto.response.CategoryResponse;
import uz.pdp.library_management_system.entity.Category;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .createdBy(categoryRequest.getCreatedBy())
                .createdAt(categoryRequest.getCreatedAt())
                .updatedBy(categoryRequest.getUpdatedBy())
                .updatedAt(categoryRequest.getUpdatedAt())
                .build();
    }

    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .libraryId(category.getLibrary().getId())
                .createdBy(category.getCreatedBy())
                .createdAt(category.getCreatedAt())
                .updatedBy(category.getUpdatedBy())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public void update(Category entity, CategoryRequest request) {
        if (request == null) {
            return;
        }
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            entity.setName(request.getName());
        }
        if (request.getDescription() != null && !request.getDescription().trim().isEmpty()) {
            entity.setDescription(request.getDescription());
        }
    }
}
