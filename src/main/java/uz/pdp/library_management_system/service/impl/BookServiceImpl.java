package uz.pdp.library_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.library_management_system.config.SessionId;
import uz.pdp.library_management_system.dto.ErrorResponse;
import uz.pdp.library_management_system.dto.request.BookRequest;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.entity.Book;
import uz.pdp.library_management_system.entity.Category;
import uz.pdp.library_management_system.exception.CustomException;
import uz.pdp.library_management_system.mapper.BookMapper;
import uz.pdp.library_management_system.repository.BookRepository;
import uz.pdp.library_management_system.repository.CategoryRepository;
import uz.pdp.library_management_system.service.BookService;
import uz.pdp.library_management_system.util.validation.BookValidation;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookValidation bookValidation;
    private final CategoryRepository categoryRepository;
    private final SessionId sessionId;

    @Override
    public Response createBook(BookRequest bookRequest) {
        List<ErrorResponse> errors = bookValidation.validate(bookRequest);
        if (!errors.isEmpty()) {
            return Response.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Book validation error")
                    .success(false)
                    .error(errors)
                    .build();
//            return Response.error("Book validation error");
        }
        Category category = categoryRepository.findById(bookRequest.getCategoryId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Category not found: " + bookRequest.getCategoryId()));
        Long authUserId = sessionId.getSessionId();
        if (!bookRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            return Response.notFound("AuthUser not found");
        }
        Book book = bookMapper.toEntity(bookRequest);
        book.setCategory(category);
        bookRepository.save(book);
        log.info("Book successfully created");
        return Response.success(bookMapper.toResponse(book), "Book successfully created");
    }

    @Override
    public Response getBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Book not found: " + bookId));
        log.info("Book successfully found");
        return Response.success(bookMapper.toResponse(book), "Book successfully found");
    }

    @Override
    public Response getAllBook() {
        List<Book> books = bookRepository.findAll();
        log.info("Book list successfully found");
        return Response.success(books.stream().map(bookMapper::toResponse).toList(),
                "Books successfully found");
    }

    @Override
    public Response updateBook(BookRequest bookRequest, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Book not found: " + bookId));
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setTotalPages(bookRequest.getTotalPages());
        book.setAvailableCopies(bookRequest.getAvailableCopies());
        Long authUserId = sessionId.getSessionId();
        if (!bookRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            return Response.notFound("AuthUser not found");
        }
        book.setCreatedBy(bookRequest.getCreatedBy());
        book.setUpdatedAt(bookRequest.getUpdatedAt());
        bookRepository.save(book);
        log.info("Book successfully updated");
        return Response.success(bookMapper.toResponse(book), "Book successfully updated");
    }

    @Override
    public Response getBookByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Category not found: " + categoryId));
        List<Book> allByCategoryId = bookRepository.findAllByCategoryId(category.getId());
        return Response.success(allByCategoryId.stream().map(bookMapper::toResponse).toList(),
                "Books successfully found");
    }
}
