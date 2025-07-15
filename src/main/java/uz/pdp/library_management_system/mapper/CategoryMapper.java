package uz.pdp.library_management_system.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.entity.Category;
import uz.pdp.library_management_system.entity.Library;
import uz.pdp.library_management_system.exception.CustomException;
import uz.pdp.library_management_system.repository.LibraryRepository;
import uz.pdp.library_management_system.dto.request.CategoryRequest;
import uz.pdp.library_management_system.dto.response.CategoryResponse;

@Component
public class CategoryMapper {

    private final LibraryRepository libraryRepository;

    public CategoryMapper(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public Category toEntity(CategoryRequest categoryRequest) {
        Library library = libraryRepository.findById(categoryRequest.getLibraryId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,"Library not found: " + categoryRequest.getLibraryId()));
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
}
