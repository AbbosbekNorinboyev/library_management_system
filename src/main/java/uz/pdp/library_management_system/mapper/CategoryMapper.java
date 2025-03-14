package uz.pdp.library_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.entity.Category;
import uz.pdp.library_management_system.request.CategoryRequest;
import uz.pdp.library_management_system.response.CategoryResponse;

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
                .name(category.getName())
                .description(category.getDescription())
                .createdBy(category.getCreatedBy())
                .createdAt(category.getCreatedAt())
                .updatedBy(category.getUpdatedBy())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
