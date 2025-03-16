package uz.pdp.library_management_system.service;

import uz.pdp.library_management_system.dto.ResponseDTO;
import uz.pdp.library_management_system.request.BookRequest;
import uz.pdp.library_management_system.response.BookResponse;

import java.util.List;

public interface BookService {
    ResponseDTO<BookResponse> createBook(BookRequest bookRequest);
    ResponseDTO<BookResponse> getBook(Long bookId);
    ResponseDTO<List<BookResponse>> getAllBook();
    ResponseDTO<Void> updateBook(BookRequest bookRequest, Long bookId);
}
