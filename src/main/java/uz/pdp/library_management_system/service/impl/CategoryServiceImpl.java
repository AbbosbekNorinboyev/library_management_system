package uz.pdp.library_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.library_management_system.dto.ResponseDTO;
import uz.pdp.library_management_system.entity.Category;
import uz.pdp.library_management_system.exception.ResourceNotFoundException;
import uz.pdp.library_management_system.mapper.CategoryMapper;
import uz.pdp.library_management_system.mapper.interfaces.CategoryMapperInterface;
import uz.pdp.library_management_system.repository.CategoryRepository;
import uz.pdp.library_management_system.request.CategoryRequest;
import uz.pdp.library_management_system.response.CategoryResponse;
import uz.pdp.library_management_system.security.SessionId;
import uz.pdp.library_management_system.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final SessionId sessionId;
    private final CategoryMapperInterface categoryMapperInterface;

    @Override
    public ResponseDTO<CategoryResponse> createCategory(CategoryRequest categoryRequest) {
        Category category = categoryMapper.toEntity(categoryRequest);
        Long authUserId = sessionId.getSessionId();
        if (!categoryRequest.getCreatedBy().equals(authUserId)) {
            return ResponseDTO.<CategoryResponse>builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("AUthUser not found")
                    .success(false)
                    .build();
        }
        categoryRepository.save(category);
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
            return ResponseDTO.<Void>builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("AUthUser not found")
                    .success(false)
                    .build();
        }
        category.setUpdatedBy(categoryRequest.getUpdatedBy());
        categoryRepository.save(category);
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Category successfully updated")
                .success(true)
                .build();
    }
}
