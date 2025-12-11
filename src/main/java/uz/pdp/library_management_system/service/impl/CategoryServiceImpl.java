package uz.pdp.library_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.library_management_system.config.SessionId;
import uz.pdp.library_management_system.dto.Empty;
import uz.pdp.library_management_system.dto.ErrorResponse;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.dto.request.CategoryRequest;
import uz.pdp.library_management_system.dto.response.CategoryResponse;
import uz.pdp.library_management_system.entity.Category;
import uz.pdp.library_management_system.entity.Library;
import uz.pdp.library_management_system.exception.CustomException;
import uz.pdp.library_management_system.mapper.CategoryMapper;
import uz.pdp.library_management_system.repository.CategoryRepository;
import uz.pdp.library_management_system.repository.LibraryRepository;
import uz.pdp.library_management_system.service.CategoryService;
import uz.pdp.library_management_system.specification.CategorySpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final SessionId sessionId;
    private final LibraryRepository libraryRepository;

    @Override
    public ResponseEntity<?> createCategory(CategoryRequest categoryRequest) {
        Category category = categoryMapper.toEntity(categoryRequest);
        Long authUserId = sessionId.getSessionId();
        if (!categoryRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("AuthUser not found")
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Library library = libraryRepository.findById(categoryRequest.getLibraryId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Library not found: " + categoryRequest.getLibraryId()));
        category.setLibrary(library);
        categoryRepository.save(category);
        log.info("Category successfully created");
        var response = Response.builder()
                .success(true)
                .data(categoryMapper.toResponse(category))
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Category not found: " + categoryId));
        log.info("Category successfully found");
        var response = Response.builder()
                .success(true)
                .data(categoryMapper.toResponse(category))
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getAllCategory(Pageable pageable) {
        Page<CategoryResponse> categories = categoryRepository.findAll(pageable)
                .map(categoryMapper::toResponse);
        log.info("Category list successfully found");
        Response<Object, Object> response = Response.builder()
                .success(true)
                .data(categories.getContent())
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> updateCategory(CategoryRequest categoryRequest, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Category not found: " + categoryId));
        categoryMapper.update(category, categoryRequest);
        Long authUserId = sessionId.getSessionId();
        if (!categoryRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("AuthUser not found")
                    .build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
        category.setUpdatedBy(categoryRequest.getUpdatedBy());
        category.setUpdatedBy(authUserId);
        categoryRepository.save(category);
        log.info("Category successfully updated");
        var response = Response.builder()
                .success(true)
                .data(categoryMapper.toResponse(category))
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getCategoryByLibraryId(Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Library not found: " + libraryId));
        List<Category> allByLibraryId = categoryRepository.findAllByLibraryId(library.getId());
        var response = Response.builder()
                .success(true)
                .data(allByLibraryId.stream().map(categoryMapper::toResponse).toList())
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> search(String name, String description) {
        Specification<Category> specification = (root, query, criteriaBuilder) -> null;
        if (name != null && !name.isEmpty()) {
            specification = specification.and(CategorySpecification.hasName(name));
        }
        if (description != null && !description.isEmpty()) {
            specification = specification.and(CategorySpecification.hasDescription(description));
        }

        var response = Response.builder()
                .success(true)
                .data(categoryRepository.findAll(specification))
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }
}
