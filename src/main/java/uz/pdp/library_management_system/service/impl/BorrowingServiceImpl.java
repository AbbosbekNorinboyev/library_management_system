package uz.pdp.library_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.library_management_system.config.SessionId;
import uz.pdp.library_management_system.dto.request.BorrowingRequest;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.entity.Book;
import uz.pdp.library_management_system.entity.Borrowing;
import uz.pdp.library_management_system.exception.CustomException;
import uz.pdp.library_management_system.mapper.BorrowingMapper;
import uz.pdp.library_management_system.repository.BookRepository;
import uz.pdp.library_management_system.repository.BorrowingRepository;
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
    public Response createBorrowing(BorrowingRequest borrowingRequest) {
        Long authUserId = sessionId.getSessionId();
        if (!borrowingRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            return Response.notFound("AuthUser not found");
        }
        Borrowing borrowing = borrowingMapper.toEntity(borrowingRequest);
        borrowingRepository.save(borrowing);
        log.info("Borrowing successfully created");
        return Response.success(borrowingMapper.toResponse(borrowing),"Book successfully created");
    }

    @Override
    public Response getBorrowing(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Borrowing not found: " + borrowingId));
        log.info("Borrowing successfully found");
        return Response.success(borrowingMapper.toResponse(borrowing),"Borrowing successfully found");
    }

    @Override
    public Response getAllBorrowing() {
        List<Borrowing> borrowings = borrowingRepository.findAll();
        log.info("Borrowing list successfully found");
        return Response.success(
                borrowings.stream().map(borrowingMapper::toResponse).toList(),
                "Borrowing successfully found");
    }

    @Override
    public Response updateBorrowing(BorrowingRequest borrowingRequest, Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Borrowing not found: " + borrowingId));
        Book book = bookRepository.findById(borrowingRequest.getBookId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Book not found: " + borrowingRequest.getBookId()));
        borrowing.setBook(book);
        borrowing.setBorrowDate(borrowingRequest.getBorrowDate());
        borrowing.setDueDate(borrowingRequest.getDueDate());
        borrowing.setReturnDate(borrowingRequest.getReturnDate());
        Long authUserId = sessionId.getSessionId();
        if (!borrowingRequest.getUpdatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            return Response.notFound("AuthUser not found");
        }
        borrowing.setUpdatedBy(borrowingRequest.getUpdatedBy());
        borrowing.setUpdatedAt(borrowingRequest.getUpdatedAt());
        borrowingRepository.save(borrowing);
        log.info("Borrowing successfully updated");
        return Response.success(borrowingMapper.toResponse(borrowing),"Borrowing successfully updated");
    }
}
