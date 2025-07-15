package uz.pdp.library_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.entity.Book;
import uz.pdp.library_management_system.entity.Category;
import uz.pdp.library_management_system.exception.ResourceNotFoundException;
import uz.pdp.library_management_system.repository.CategoryRepository;
import uz.pdp.library_management_system.request.BookRequest;
import uz.pdp.library_management_system.response.BookResponse;

@Component
public class BookMapper {

    private final CategoryRepository categoryRepository;

    public BookMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Book toEntity(BookRequest bookRequest) {
        Category category = categoryRepository.findById(bookRequest.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category not found: " + bookRequest.getCategoryId()));
        return Book.builder()
                .author(bookRequest.getAuthor())
                .title(bookRequest.getTitle())
                .totalPages(bookRequest.getTotalPages())
                .availableCopies(bookRequest.getAvailableCopies())
                .category(category)
                .createdBy(bookRequest.getCreatedBy())
                .createdAt(bookRequest.getCreatedAt())
                .updatedBy(bookRequest.getUpdatedBy())
                .updatedAt(bookRequest.getCreatedAt())
                .build();
    }

    public BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .title(book.getTitle())
                .totalPages(book.getTotalPages())
                .availableCopies(book.getAvailableCopies())
                .categoryId(book.getCategory().getId())
                .createdBy(book.getCreatedBy())
                .createdAt(book.getCreatedAt())
                .updatedBy(book.getUpdatedBy())
                .updatedAt(book.getCreatedAt())
                .build();
    }
}
