package uz.pdp.library_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.library_management_system.dto.ResponseDTO;
import uz.pdp.library_management_system.request.CategoryRequest;
import uz.pdp.library_management_system.response.CategoryResponse;
import uz.pdp.library_management_system.service.impl.CategoryServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @PostMapping("/create")
    public ResponseDTO<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @GetMapping("/{categoryId}")
    public ResponseDTO<CategoryResponse> getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @GetMapping
    public ResponseDTO<List<CategoryResponse>> getAllResponse() {
        return categoryService.getAllCategory();
    }

    @PutMapping("/update/{categoryId}")
    public ResponseDTO<Void> updateCategory(@RequestBody @Valid CategoryRequest categoryRequest,
                                            @PathVariable Long categoryId) {
        return categoryService.updateCategory(categoryRequest, categoryId);
    }
}
