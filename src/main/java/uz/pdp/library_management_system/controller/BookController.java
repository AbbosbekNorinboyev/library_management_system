package uz.pdp.library_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.library_management_system.dto.request.BookRequest;
import uz.pdp.library_management_system.dto.response.Response;
import uz.pdp.library_management_system.service.impl.BookServiceImpl;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookServiceImpl bookService;

    @PostMapping("/create")
    public Response createBook(@RequestBody BookRequest bookRequest) {
        return bookService.createBook(bookRequest);
    }

    @GetMapping("/{bookId}")
    public Response getBook(@PathVariable Long bookId) {
        return bookService.getBook(bookId);
    }

    @GetMapping
    public Response getAllBook() {
        return bookService.getAllBook();
    }

    @PutMapping("/update/{bookId}")
    public Response updateBook(@RequestBody BookRequest bookRequest,
                               @PathVariable Long bookId) {
        return bookService.updateBook(bookRequest, bookId);
    }
}
