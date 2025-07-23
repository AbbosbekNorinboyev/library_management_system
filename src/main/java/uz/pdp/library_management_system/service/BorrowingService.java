package uz.pdp.library_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.library_management_system.dto.request.BorrowingRequest;
import uz.pdp.library_management_system.dto.Response;

public interface BorrowingService {
    Response createBorrowing(BorrowingRequest borrowingRequest);

    Response getBorrowing(Long borrowingId);

    Response getAllBorrowing(Pageable pageable);

    Response updateBorrowing(BorrowingRequest borrowingRequest, Long borrowingId);
}
