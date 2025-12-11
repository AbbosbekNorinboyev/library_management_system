package uz.pdp.library_management_system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.dto.request.LibraryRequest;
import uz.pdp.library_management_system.service.LibraryService;

@RestController
@RequestMapping("/api/libraries")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @PostMapping("/create")
    public Response createLibrary(@RequestBody LibraryRequest libraryRequest) {
        return libraryService.createLibrary(libraryRequest);
    }

    @GetMapping("/get")
    public Response getLibrary(@RequestParam("libraryId") Long libraryId) {
        return libraryService.getLibrary(libraryId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllLibrary(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return libraryService.getAllLibrary(PageRequest.of(page, size));
    }

    @PutMapping("/update")
    public Response createLibrary(@RequestBody LibraryRequest libraryRequest,
                                  @RequestParam("libraryId") Long libraryId) {
        return libraryService.updateLibrary(libraryRequest, libraryId);
    }
}
