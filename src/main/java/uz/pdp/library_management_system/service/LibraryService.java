package uz.pdp.library_management_system.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.library_management_system.dto.request.LibraryRequest;
import uz.pdp.library_management_system.dto.Response;

public interface LibraryService {
    Response createLibrary(LibraryRequest libraryRequest);

    Response getLibrary(Long libraryId);

    Response getAllLibrary(Pageable pageable);

    Response updateLibrary(LibraryRequest libraryRequest, Long libraryId);
}
