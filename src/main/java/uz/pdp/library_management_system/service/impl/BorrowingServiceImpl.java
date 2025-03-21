package uz.pdp.library_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.library_management_system.dto.ResponseDTO;
import uz.pdp.library_management_system.entity.Book;
import uz.pdp.library_management_system.entity.Borrowing;
import uz.pdp.library_management_system.exception.ResourceNotFoundException;
import uz.pdp.library_management_system.mapper.BorrowingMapper;
import uz.pdp.library_management_system.repository.BookRepository;
import uz.pdp.library_management_system.repository.BorrowingRepository;
import uz.pdp.library_management_system.request.BorrowingRequest;
import uz.pdp.library_management_system.response.BorrowingResponse;
import uz.pdp.library_management_system.security.SessionId;
import uz.pdp.library_management_system.service.BorrowingService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowingServiceImpl implements BorrowingService {
    private final BorrowingMapper borrowingMapper;
    private final BorrowingRepository borrowingRepository;
    private final SessionId sessionId;
    private final BookRepository bookRepository;

    @Override
    public ResponseDTO<BorrowingResponse> createBorrowing(BorrowingRequest borrowingRequest) {
        Long authUserId = sessionId.getSessionId();
        if (!borrowingRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            return ResponseDTO.<BorrowingResponse>builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("AuthUser not found")
                    .success(false)
                    .build();
        }
        Borrowing borrowing = borrowingMapper.toEntity(borrowingRequest);
        borrowingRepository.save(borrowing);
        log.info("Borrowing successfully created");
        return ResponseDTO.<BorrowingResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Borrowing successfully created")
                .success(true)
                .data(borrowingMapper.toResponse(borrowing))
                .build();
    }

    @Override
    public ResponseDTO<BorrowingResponse> getBorrowing(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing not found: " + borrowingId));
        log.info("Borrowing successfully found");
        return ResponseDTO.<BorrowingResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Borrowing successfully found")
                .success(true)
                .data(borrowingMapper.toResponse(borrowing))
                .build();
    }

    @Override
    public ResponseDTO<List<BorrowingResponse>> getAllBorrowing() {
        List<Borrowing> borrowings = borrowingRepository.findAll();
        log.info("Borrowing list successfully found");
        return ResponseDTO.<List<BorrowingResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Borrowing list successfully found")
                .success(true)
                .data(borrowings.stream().map(borrowingMapper::toResponse).toList())
                .build();
    }

    @Override
    public ResponseDTO<Void> updateBorrowing(BorrowingRequest borrowingRequest, Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing not found: " + borrowingId));
        Book book = bookRepository.findById(borrowingRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + borrowingRequest.getBookId()));
        borrowing.setBook(book);
        borrowing.setBorrowDate(borrowingRequest.getBorrowDate());
        borrowing.setDueDate(borrowingRequest.getDueDate());
        borrowing.setReturnDate(borrowingRequest.getReturnDate());
        Long authUserId = sessionId.getSessionId();
        if (!borrowingRequest.getUpdatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            return ResponseDTO.<Void>builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("AuthUser not found")
                    .success(false)
                    .build();
        }
        borrowing.setUpdatedBy(borrowingRequest.getUpdatedBy());
        borrowing.setUpdatedAt(borrowingRequest.getUpdatedAt());
        borrowingRepository.save(borrowing);
        log.info("Borrowing successfully updated");
        return ResponseDTO.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Borrowing successfully updated")
                .success(true)
                .build();
    }
}
