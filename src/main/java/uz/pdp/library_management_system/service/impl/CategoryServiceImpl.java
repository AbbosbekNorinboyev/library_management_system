package uz.pdp.library_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.library_management_system.dto.ResponseDTO;
import uz.pdp.library_management_system.entity.Category;
import uz.pdp.library_management_system.entity.Library;
import uz.pdp.library_management_system.exception.ResourceNotFoundException;
import uz.pdp.library_management_system.mapper.CategoryMapper;
import uz.pdp.library_management_system.mapper.interfaces.CategoryMapperInterface;
import uz.pdp.library_management_system.repository.CategoryRepository;
import uz.pdp.library_management_system.repository.LibraryRepository;
import uz.pdp.library_management_system.request.CategoryRequest;
import uz.pdp.library_management_system.response.CategoryResponse;
import uz.pdp.library_management_system.config.SessionId;
import uz.pdp.library_management_system.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final SessionId sessionId;
    private final CategoryMapperInterface categoryMapperInterface;
    private final LibraryRepository libraryRepository;

    @Override
    public ResponseDTO<CategoryResponse> createCategory(CategoryRequest categoryRequest) {
        Category category = categoryMapper.toEntity(categoryRequest);
        Long authUserId = sessionId.getSessionId();
        if (!categoryRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            return ResponseDTO.<CategoryResponse>builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("AUthUser not found")
                    .success(false)
                    .build();
        }
        Library library = libraryRepository.findById(categoryRequest.getLibraryId())
                .orElseThrow(() -> new ResourceNotFoundException("Library not found: " + categoryRequest.getLibraryId()));
        category.setLibrary(library);
        categoryRepository.save(category);
        log.info("Category successfully created");
        return ResponseDTO.<CategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Category successfully created")
                .success(true)
                .data(categoryMapper.toResponse(category))
                .build();
    }

    @Override
    public ResponseDTO<CategoryResponse> getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryId));
        log.info("Category successfully found");
        return ResponseDTO.<CategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Category successfully found")
                .success(true)
                .data(categoryMapperInterface.toResponse(category))
                .build();
    }

    @Override
    public ResponseDTO<List<CategoryResponse>> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        log.info("Category list successfully found");
        return ResponseDTO.<List<CategoryResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Category list successfully found")
                .success(true)
                .data(categories.stream().map(categoryMapper::toResponse).toList())
                .build();
    }

    @Override
    public ResponseDTO<Void> updateCategory(CategoryRequest categoryRequest, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryId));
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setUpdatedAt(categoryRequest.getUpdatedAt());
        Long authUserId = sessionId.getSessionId();
        if (!categoryRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            return ResponseDTO.<Void>builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("AUthUser not found")
                    .success(false)
                    .build();
        }
        category.setUpdatedBy(categoryRequest.getUpdatedBy());
        categoryRepository.save(category);
        log.info("Category successfully updated");
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Category successfully updated")
                .success(true)
                .build();
    }
}
