package uz.pdp.library_management_system.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.entity.Book;
import uz.pdp.library_management_system.entity.Borrowing;
import uz.pdp.library_management_system.exception.CustomException;
import uz.pdp.library_management_system.repository.BookRepository;
import uz.pdp.library_management_system.dto.request.BorrowingRequest;
import uz.pdp.library_management_system.dto.response.BorrowingResponse;

@Component
@RequiredArgsConstructor
public class BorrowingMapper {

    private final BookRepository bookRepository;

    public Borrowing toEntity(BorrowingRequest borrowingRequest) {
        Book book = bookRepository.findById(borrowingRequest.getBookId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Book not found: " + borrowingRequest.getBookId()));
        return Borrowing.builder()
                .book(book)
                .borrowDate(borrowingRequest.getBorrowDate())
                .dueDate(borrowingRequest.getDueDate())
                .returnDate(borrowingRequest.getReturnDate())
                .status(borrowingRequest.getStatus())
                .createdBy(borrowingRequest.getCreatedBy())
                .createdAt(borrowingRequest.getCreatedAt())
                .updatedBy(borrowingRequest.getUpdatedBy())
                .updatedAt(borrowingRequest.getUpdatedAt())
                .build();
    }

    public BorrowingResponse toResponse(Borrowing borrowing) {
        return BorrowingResponse.builder()
                .id(borrowing.getId())
                .bookId(borrowing.getBook().getId())
                .borrowDate(borrowing.getBorrowDate())
                .dueDate(borrowing.getDueDate())
                .returnDate(borrowing.getReturnDate())
                .status(borrowing.getStatus())
                .createdBy(borrowing.getCreatedBy())
                .createdAt(borrowing.getCreatedAt())
                .updatedBy(borrowing.getUpdatedBy())
                .updatedAt(borrowing.getUpdatedAt())
                .build();
    }
}
