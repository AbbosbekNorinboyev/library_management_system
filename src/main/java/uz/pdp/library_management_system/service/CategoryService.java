package uz.pdp.library_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.dto.request.CategoryRequest;

public interface CategoryService {
    Response createCategory(CategoryRequest categoryRequest);

    Response getCategory(Long categoryId);

    Response getAllCategory(Pageable pageable);

    Response updateCategory(CategoryRequest categoryRequest, Long categoryId);

    Response getCategoryByLibraryId(Long libraryId);

    Response search(String name, String description);
}
