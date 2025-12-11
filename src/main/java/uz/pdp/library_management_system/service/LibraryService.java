package uz.pdp.library_management_system.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import uz.pdp.library_management_system.dto.request.LibraryRequest;

public interface LibraryService {
    ResponseEntity<?> createLibrary(LibraryRequest libraryRequest);

    ResponseEntity<?> getLibrary(Long libraryId);

    ResponseEntity<?> getAllLibrary(Pageable pageable);

    ResponseEntity<?> updateLibrary(LibraryRequest libraryRequest, Long libraryId);
}
