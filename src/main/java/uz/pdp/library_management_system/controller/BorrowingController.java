package uz.pdp.library_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping("/get")
    public Response getBorrowing(@RequestParam("borrowingId") Long borrowingId) {
        return borrowingService.getBorrowing(borrowingId);
    }

    @GetMapping("/getAll")
    public Response getAllBorrowing(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return borrowingService.getAllBorrowing(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public Response updateBorrowing(@RequestBody BorrowingRequest borrowingRequest,
                                    @RequestParam("borrowingId") Long borrowingId) {
        return borrowingService.updateBorrowing(borrowingRequest, borrowingId);
    }
}
