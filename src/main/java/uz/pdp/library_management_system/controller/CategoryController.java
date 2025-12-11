package uz.pdp.library_management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.dto.request.CategoryRequest;
import uz.pdp.library_management_system.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create")
    public Response createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @GetMapping("/get")
    public Response getCategory(@RequestParam("categoryId") Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllResponse(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                            @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return categoryService.getAllCategory(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public Response updateCategory(@RequestBody @Valid CategoryRequest categoryRequest,
                                   @RequestParam("categoryId") Long categoryId) {
        return categoryService.updateCategory(categoryRequest, categoryId);
    }

    @GetMapping("/getCategoryByLibraryId")
    public Response getCategoryByLibraryId(@RequestParam("libraryId") Long libraryId) {
        return categoryService.getCategoryByLibraryId(libraryId);
    }

    @GetMapping("/search")
    public Response search(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String description) {
        return categoryService.search(name, description);
    }
}
