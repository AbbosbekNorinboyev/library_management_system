package uz.pdp.library_management_system.service;

import uz.pdp.library_management_system.dto.ResponseDTO;
import uz.pdp.library_management_system.dto.request.BorrowingRequest;
import uz.pdp.library_management_system.dto.response.BorrowingResponse;

import java.util.List;

public interface BorrowingService {
    ResponseDTO<BorrowingResponse> createBorrowing(BorrowingRequest borrowingRequest);

    ResponseDTO<BorrowingResponse> getBorrowing(Long borrowingId);

    ResponseDTO<List<BorrowingResponse>> getAllBorrowing();

    ResponseDTO<Void> updateBorrowing(BorrowingRequest borrowingRequest, Long borrowingId);
}
