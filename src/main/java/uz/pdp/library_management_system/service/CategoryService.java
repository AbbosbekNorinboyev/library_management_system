package uz.pdp.library_management_system.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.pdp.library_management_system.dto.request.CategoryRequest;

public interface CategoryService {
    ResponseEntity<?> createCategory(CategoryRequest categoryRequest);

    ResponseEntity<?> getCategory(Long categoryId);

    ResponseEntity<?> getAllCategory(Pageable pageable);

    ResponseEntity<?> updateCategory(CategoryRequest categoryRequest, Long categoryId);

    ResponseEntity<?> getCategoryByLibraryId(Long libraryId);

    ResponseEntity<?> search(String name, String description);
}
