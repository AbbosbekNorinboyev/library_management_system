package uz.pdp.library_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.entity.Category;
import uz.pdp.library_management_system.entity.Library;
import uz.pdp.library_management_system.exception.ResourceNotFoundException;
import uz.pdp.library_management_system.repository.LibraryRepository;
import uz.pdp.library_management_system.request.CategoryRequest;
import uz.pdp.library_management_system.response.CategoryResponse;

@Component
public class CategoryMapper {

    private final LibraryRepository libraryRepository;

    public CategoryMapper(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public Category toEntity(CategoryRequest categoryRequest) {
        Library library = libraryRepository.findById(categoryRequest.getLibraryId())
                .orElseThrow(() -> new ResourceNotFoundException("Library not found: " + categoryRequest.getLibraryId()));
        return Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .library(library)
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
                .libraryId(category.getLibrary().getId())
                .createdBy(category.getCreatedBy())
                .createdAt(category.getCreatedAt())
                .updatedBy(category.getUpdatedBy())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
