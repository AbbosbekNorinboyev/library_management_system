package uz.pdp.library_management_system.service;

import uz.pdp.library_management_system.dto.request.BookRequest;
import uz.pdp.library_management_system.dto.response.Response;

public interface BookService {
    Response createBook(BookRequest bookRequest);

    Response getBook(Long bookId);

    Response getAllBook();

    Response updateBook(BookRequest bookRequest, Long bookId);
}
