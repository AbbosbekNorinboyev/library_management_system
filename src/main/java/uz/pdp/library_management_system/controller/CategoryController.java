package uz.pdp.library_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.library_management_system.dto.request.CategoryRequest;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.service.impl.CategoryServiceImpl;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @PostMapping("/create")
    public Response createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @GetMapping("/{categoryId}")
    public Response getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @GetMapping
    public Response getAllResponse() {
        return categoryService.getAllCategory();
    }

    @PutMapping("/update/{categoryId}")
    public Response updateCategory(@RequestBody @Valid CategoryRequest categoryRequest,
                                   @PathVariable Long categoryId) {
        return categoryService.updateCategory(categoryRequest, categoryId);
    }
}
