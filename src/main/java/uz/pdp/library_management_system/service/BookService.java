package uz.pdp.library_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.library_management_system.dto.request.BookRequest;
import uz.pdp.library_management_system.dto.Response;

public interface BookService {
    Response createBook(BookRequest bookRequest);

    Response getBook(Long bookId);

    Response getAllBook(Pageable pageable);

    Response updateBook(BookRequest bookRequest, Long bookId);

    Response getBookByCategoryId(Long categoryId);
}
