package uz.pdp.library_management_system.controller;

import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{libraryId}")
    public Response getLibrary(@PathVariable Long libraryId) {
        return libraryService.getLibrary(libraryId);
    }

    @GetMapping
    public Response getAllLibrary() {
        return libraryService.getAllLibrary();
    }

    @PutMapping("/update/{libraryId}")
    public Response createLibrary(@RequestBody LibraryRequest libraryRequest,
                                  @PathVariable Long libraryId) {
        return libraryService.updateLibrary(libraryRequest, libraryId);
    }
}
