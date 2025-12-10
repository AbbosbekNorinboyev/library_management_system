package uz.pdp.library_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.library_management_system.config.SessionId;
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
    public Response getAllCategory(Pageable pageable) {
        Page<CategoryResponse> categories = categoryRepository.findAll(pageable)
                .map(categoryMapper::toResponse);
        log.info("Category list successfully found");
        return Response.success(categories, "Categories successfully found");
    }

    @Override
    public Response updateCategory(CategoryRequest categoryRequest, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Category not found: " + categoryId));
        categoryMapper.update(category, categoryRequest);
        Long authUserId = sessionId.getSessionId();
        if (!categoryRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            return Response.notFound("AuthUser not found");
        }
        category.setUpdatedBy(categoryRequest.getUpdatedBy());
        category.setUpdatedBy(authUserId);
        categoryRepository.save(category);
        log.info("Category successfully updated");
        return Response.success(categoryMapper.toResponse(category), "Category successfully updated");
    }

    @Override
    public Response getCategoryByLibraryId(Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Library not found: " + libraryId));
        List<Category> allByLibraryId = categoryRepository.findAllByLibraryId(library.getId());
        return Response.success(allByLibraryId.stream().map(categoryMapper::toResponse).toList(),
                "Categories successfully found by libraryId");
    }

    @Override
    public Response search(String name, String description) {
        Specification<Category> specification = (root, query, criteriaBuilder) -> null;
        if (name != null && !name.isEmpty()) {
            specification = specification.and(CategorySpecification.hasName(name));
        }
        if (description != null && !description.isEmpty()) {
            specification = specification.and(CategorySpecification.hasDescription(description));
        }

        return Response.success(categoryRepository.findAll(specification), "Category specification");
    }
}
