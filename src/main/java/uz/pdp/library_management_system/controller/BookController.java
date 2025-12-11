package uz.pdp.library_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.dto.request.BookRequest;
import uz.pdp.library_management_system.service.BookService;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/create")
    public Response createBook(@RequestBody BookRequest bookRequest) {
        return bookService.createBook(bookRequest);
    }

    @GetMapping("/get")
    public Response getBook(@RequestParam("bookId") Long bookId) {
        return bookService.getBook(bookId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBook(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return bookService.getAllBook(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public Response updateBook(@RequestBody BookRequest bookRequest,
                               @RequestParam("bookId") Long bookId) {
        return bookService.updateBook(bookRequest, bookId);
    }

    @GetMapping("/getBookByCategoryId")
    public Response getBookByCategoryId(@RequestParam("categoryId") Long categoryId) {
        return bookService.getBookByCategoryId(categoryId);
    }

    @GetMapping("/search")
    public Response search(@RequestParam(required = false) String title,
                           @RequestParam(required = false) String author,
                           @RequestParam(required = false) Integer totalPages,
                           @RequestParam(required = false) Long availableCopies) {
        return bookService.search(title, author, totalPages, availableCopies);
    }
}
