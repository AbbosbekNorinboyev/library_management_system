package uz.pdp.library_management_system.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.dto.request.BorrowingRequest;

public interface BorrowingService {
    Response createBorrowing(BorrowingRequest borrowingRequest);

    ResponseEntity<?> getBorrowing(Long borrowingId);

    ResponseEntity<?> getAllBorrowing(Pageable pageable);

    Response updateBorrowing(BorrowingRequest borrowingRequest, Long borrowingId);
}
