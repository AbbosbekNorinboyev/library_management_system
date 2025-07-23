package uz.pdp.library_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.dto.request.BorrowingRequest;
import uz.pdp.library_management_system.service.BorrowingService;

@RestController
@RequestMapping("/api/borrowings")
@RequiredArgsConstructor
public class BorrowingController {
    private final BorrowingService borrowingService;

    @PostMapping("/create")
    public Response createBorrowing(@RequestBody BorrowingRequest borrowingRequest) {
        return borrowingService.createBorrowing(borrowingRequest);
    }

    @GetMapping("/{borrowingId}")
    public Response getBorrowing(@PathVariable Long borrowingId) {
        return borrowingService.getBorrowing(borrowingId);
    }

    @GetMapping
    public Response getAllBorrowing() {
        return borrowingService.getAllBorrowing();
    }

    @PutMapping("/update/{borrowingId}")
    public Response updateBorrowing(@RequestBody BorrowingRequest borrowingRequest,
                                    @PathVariable Long borrowingId) {
        return borrowingService.updateBorrowing(borrowingRequest, borrowingId);
    }
}
