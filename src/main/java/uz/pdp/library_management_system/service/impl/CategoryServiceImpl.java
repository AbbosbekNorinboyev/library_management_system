package uz.pdp.library_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.library_management_system.config.SessionId;
import uz.pdp.library_management_system.dto.request.CategoryRequest;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.entity.Category;
import uz.pdp.library_management_system.entity.Library;
import uz.pdp.library_management_system.exception.CustomException;
import uz.pdp.library_management_system.mapper.CategoryMapper;
import uz.pdp.library_management_system.mapper.interfaces.CategoryMapperInterface;
import uz.pdp.library_management_system.repository.CategoryRepository;
import uz.pdp.library_management_system.repository.LibraryRepository;
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
    public Response createCategory(CategoryRequest categoryRequest) {
        Category category = categoryMapper.toEntity(categoryRequest);
        Long authUserId = sessionId.getSessionId();
        if (!categoryRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            return Response.notFound("AuthUser not found");
        }
        Library library = libraryRepository.findById(categoryRequest.getLibraryId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Library not found: " + categoryRequest.getLibraryId()));
        category.setLibrary(library);
        categoryRepository.save(category);
        log.info("Category successfully created");
        return Response.success(categoryMapper.toResponse(category), "Category successfully created");
    }

    @Override
    public Response getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Category not found: " + categoryId));
        log.info("Category successfully found");
        return Response.success(categoryMapper.toResponse(category), "Category successfully found");
    }

    @Override
    public Response getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        log.info("Category list successfully found");
        return Response.success(categories.stream().map(categoryMapper::toResponse).toList(),
                "Categories successfully found");
    }

    @Override
    public Response updateCategory(CategoryRequest categoryRequest, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Category not found: " + categoryId));
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setUpdatedAt(categoryRequest.getUpdatedAt());
        Long authUserId = sessionId.getSessionId();
        if (!categoryRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            return Response.notFound("AuthUser not found");
        }
        category.setUpdatedBy(categoryRequest.getUpdatedBy());
        categoryRepository.save(category);
        log.info("Category successfully updated");
        return Response.success(categoryMapper.toResponse(category), "Category successfully updated");
    }
}
