package uz.pdp.library_management_system.service;

import uz.pdp.library_management_system.dto.ResponseDTO;
import uz.pdp.library_management_system.request.LibraryRequest;
import uz.pdp.library_management_system.response.LibraryResponse;

import java.util.List;

public interface LibraryService {
    ResponseDTO<LibraryResponse> createLibrary(LibraryRequest libraryRequest);

    ResponseDTO<LibraryResponse> getLibrary(Long libraryId);

    ResponseDTO<List<LibraryResponse>> getAllLibrary();

    ResponseDTO<Void> updateLibrary(LibraryRequest libraryRequest, Long libraryId);
}
