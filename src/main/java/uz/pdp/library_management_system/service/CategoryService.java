package uz.pdp.library_management_system.service;

import uz.pdp.library_management_system.dto.ResponseDTO;
import uz.pdp.library_management_system.dto.request.CategoryRequest;
import uz.pdp.library_management_system.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    ResponseDTO<CategoryResponse> createCategory(CategoryRequest categoryRequest);
    ResponseDTO<CategoryResponse> getCategory(Long categoryId);
    ResponseDTO<List<CategoryResponse>> getAllCategory();
    ResponseDTO<Void> updateCategory(CategoryRequest categoryRequest, Long categoryId);
}
