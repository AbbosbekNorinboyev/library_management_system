package uz.pdp.library_management_system.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.pdp.library_management_system.dto.request.BorrowingRequest;

public interface BorrowingService {
    ResponseEntity<?> createBorrowing(BorrowingRequest borrowingRequest);

    ResponseEntity<?> getBorrowing(Long borrowingId);

    ResponseEntity<?> getAllBorrowing(Pageable pageable);

    ResponseEntity<?> updateBorrowing(BorrowingRequest borrowingRequest, Long borrowingId);
}
