package uz.pdp.library_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.library_management_system.config.SessionId;
import uz.pdp.library_management_system.dto.Empty;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.dto.request.BookRequest;
import uz.pdp.library_management_system.dto.response.BookResponse;
import uz.pdp.library_management_system.entity.Book;
import uz.pdp.library_management_system.entity.Category;
import uz.pdp.library_management_system.exception.CustomException;
import uz.pdp.library_management_system.mapper.BookMapper;
import uz.pdp.library_management_system.repository.BookRepository;
import uz.pdp.library_management_system.repository.CategoryRepository;
import uz.pdp.library_management_system.service.BookService;
import uz.pdp.library_management_system.specification.BookSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final SessionId sessionId;

    @Override
    public Response createBook(BookRequest bookRequest) {
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
        return Response.success(bookMapper.toResponse(book));
    }

    @Override
    public ResponseEntity<?> getBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Book not found: " + bookId));
        log.info("Book successfully found");
        var response = Response.builder()
                .success(true)
                .data(bookMapper.toResponse(book))
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getAllBook(Pageable pageable) {
        Page<BookResponse> books = bookRepository.findAll(pageable)
                .map(bookMapper::toResponse);
        log.info("Book list successfully found");
        Response<Object, Object> response = Response.builder()
                .success(true)
                .data(books.getContent())
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public Response updateBook(BookRequest bookRequest, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Book not found: " + bookId));
        bookMapper.update(book, bookRequest);
        Long authUserId = sessionId.getSessionId();
        if (!bookRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            return Response.notFound("AuthUser not found");
        }
        book.setCreatedBy(bookRequest.getCreatedBy());
        book.setUpdatedAt(bookRequest.getUpdatedAt());
        bookRepository.save(book);
        log.info("Book successfully updated");
        return Response.success(bookMapper.toResponse(book));
    }

    @Override
    public ResponseEntity<?> getBookByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Category not found: " + categoryId));
        List<Book> allByCategoryId = bookRepository.findAllByCategoryId(category.getId());
        var response = Response.builder()
                .success(true)
                .data(allByCategoryId.stream().map(bookMapper::toResponse).toList())
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public Response search(String title, String author, Integer totalPages, Long availableCopies) {
        Specification<Book> specification = (root, query, criteriaBuilder) -> null;
        if (title != null && !title.isEmpty()) {
            specification = specification.and(BookSpecification.hasTitle(title));
        }
        if (author != null && !author.isEmpty()) {
            specification = specification.and(BookSpecification.hasAuthor(author));
        }
        if (totalPages != null) {
            specification = specification.and(BookSpecification.hasTotalPages(totalPages));
        }
        if (availableCopies != null) {
            specification = specification.and(BookSpecification.hasAvailableCopies(availableCopies));
        }
        return Response.success(bookRepository.findAll(specification));
    }
}
