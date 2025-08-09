package uz.pdp.library_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.dto.request.BookRequest;
import uz.pdp.library_management_system.dto.response.BookResponse;
import uz.pdp.library_management_system.entity.Book;

@Component
public class BookMapper {
    public Book toEntity(BookRequest bookRequest) {
        return Book.builder()
                .author(bookRequest.getAuthor())
                .title(bookRequest.getTitle())
                .totalPages(bookRequest.getTotalPages())
                .availableCopies(bookRequest.getAvailableCopies())
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

    public void update(Book entity, BookRequest request) {
        if (request == null) {
            return;
        }
        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            entity.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null && !request.getAuthor().trim().isEmpty()) {
            entity.setAuthor(request.getAuthor());
        }
        if (request.getTotalPages() != null) {
            entity.setTotalPages(request.getTotalPages());
        }
        if (request.getAvailableCopies() != null) {
            entity.setAvailableCopies(request.getAvailableCopies());
        }
    }
}
