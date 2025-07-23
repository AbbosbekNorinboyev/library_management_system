package uz.pdp.library_management_system.service;

import uz.pdp.library_management_system.dto.request.CategoryRequest;
import uz.pdp.library_management_system.dto.Response;

public interface CategoryService {
    Response createCategory(CategoryRequest categoryRequest);

    Response getCategory(Long categoryId);

    Response getAllCategory();

    Response updateCategory(CategoryRequest categoryRequest, Long categoryId);

    Response getCategoryByLibraryId(Long libraryId);
}
