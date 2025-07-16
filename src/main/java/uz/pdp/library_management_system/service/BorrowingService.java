package uz.pdp.library_management_system.service;

import uz.pdp.library_management_system.dto.request.BorrowingRequest;
import uz.pdp.library_management_system.dto.Response;

public interface BorrowingService {
    Response createBorrowing(BorrowingRequest borrowingRequest);

    Response getBorrowing(Long borrowingId);

    Response getAllBorrowing();

    Response updateBorrowing(BorrowingRequest borrowingRequest, Long borrowingId);
}
