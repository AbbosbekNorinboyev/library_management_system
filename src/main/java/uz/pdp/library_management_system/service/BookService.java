package uz.pdp.library_management_system.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.dto.request.BookRequest;

public interface BookService {
    Response createBook(BookRequest bookRequest);

    Response getBook(Long bookId);

    ResponseEntity<?> getAllBook(Pageable pageable);

    Response updateBook(BookRequest bookRequest, Long bookId);

    Response getBookByCategoryId(Long categoryId);

    Response search(String title, String author, Integer totalPages, Long availableCopies);
}
