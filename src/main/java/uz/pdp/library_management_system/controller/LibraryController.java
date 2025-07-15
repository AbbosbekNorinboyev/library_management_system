package uz.pdp.library_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.library_management_system.dto.ResponseDTO;
import uz.pdp.library_management_system.dto.request.LibraryRequest;
import uz.pdp.library_management_system.dto.response.LibraryResponse;
import uz.pdp.library_management_system.service.impl.LibraryServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/libraries")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryServiceImpl libraryService;

    @PostMapping("/create")
    public ResponseDTO<LibraryResponse> createLibrary(@RequestBody LibraryRequest libraryRequest) {
        return libraryService.createLibrary(libraryRequest);
    }

    @GetMapping("/{libraryId}")
    public ResponseDTO<LibraryResponse> getLibrary(@PathVariable Long libraryId) {
        return libraryService.getLibrary(libraryId);
    }

    @GetMapping
    public ResponseDTO<List<LibraryResponse>> getAllLibrary() {
        return libraryService.getAllLibrary();
    }

    @PutMapping("/update/{libraryId}")
    public ResponseDTO<Void> createLibrary(@RequestBody LibraryRequest libraryRequest,
                                           @PathVariable Long libraryId) {
        return libraryService.updateLibrary(libraryRequest, libraryId);
    }
}
