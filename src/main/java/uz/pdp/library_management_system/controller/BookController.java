package uz.pdp.library_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.library_management_system.dto.ResponseDTO;
import uz.pdp.library_management_system.request.BookRequest;
import uz.pdp.library_management_system.response.BookResponse;
import uz.pdp.library_management_system.service.impl.BookServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookServiceImpl bookService;

    @PostMapping("/create")
    public ResponseDTO<BookResponse> createBook(@RequestBody BookRequest bookRequest) {
        return bookService.createBook(bookRequest);
    }

    @GetMapping("/{bookId}")
    public ResponseDTO<BookResponse> getBook(@PathVariable Long bookId) {
        return bookService.getBook(bookId);
    }

    @GetMapping
    public ResponseDTO<List<BookResponse>> getAllBook() {
        return bookService.getAllBook();
    }

    @PutMapping("/update/{bookId}")
    public ResponseDTO<Void> updateBook(@RequestBody BookRequest bookRequest,
                                        @PathVariable Long bookId) {
        return bookService.updateBook(bookRequest, bookId);
    }
}
