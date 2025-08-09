package uz.pdp.library_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.dto.request.BorrowingRequest;
import uz.pdp.library_management_system.dto.response.BorrowingResponse;
import uz.pdp.library_management_system.entity.Borrowing;

@Component
public class BorrowingMapper {
    public Borrowing toEntity(BorrowingRequest borrowingRequest) {
        return Borrowing.builder()
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

    public void update(Borrowing entity, BorrowingRequest request) {
        if (request == null) {
            return;
        }
        if (request.getBorrowDate() != null) {
            entity.setBorrowDate(request.getBorrowDate());
        }
        if (request.getDueDate() != null) {
            entity.setDueDate(request.getDueDate());
        }
        if (request.getReturnDate() != null) {
            entity.setReturnDate(request.getReturnDate());
        }
    }
}
