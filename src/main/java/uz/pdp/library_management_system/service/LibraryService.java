package uz.pdp.library_management_system.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.dto.request.LibraryRequest;

public interface LibraryService {
    Response createLibrary(LibraryRequest libraryRequest);

    Response getLibrary(Long libraryId);

    ResponseEntity<?> getAllLibrary(Pageable pageable);

    Response updateLibrary(LibraryRequest libraryRequest, Long libraryId);
}
