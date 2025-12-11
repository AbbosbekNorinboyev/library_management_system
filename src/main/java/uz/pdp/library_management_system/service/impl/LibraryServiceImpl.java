package uz.pdp.library_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.library_management_system.config.SessionId;
import uz.pdp.library_management_system.dto.Empty;
import uz.pdp.library_management_system.dto.ErrorResponse;
import uz.pdp.library_management_system.dto.Response;
import uz.pdp.library_management_system.dto.request.LibraryRequest;
import uz.pdp.library_management_system.dto.response.LibraryResponse;
import uz.pdp.library_management_system.entity.Library;
import uz.pdp.library_management_system.exception.CustomException;
import uz.pdp.library_management_system.mapper.LibraryMapper;
import uz.pdp.library_management_system.repository.LibraryRepository;
import uz.pdp.library_management_system.service.LibraryService;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryServiceImpl implements LibraryService {
    private final LibraryMapper libraryMapper;
    private final LibraryRepository libraryRepository;
    private final SessionId sessionId;

    @Override
    public ResponseEntity<?> createLibrary(LibraryRequest libraryRequest) {
        Long authUserId = sessionId.getSessionId();
        if (!libraryRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("AuthUser not found")
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        Library library = libraryMapper.toEntity(libraryRequest);
        libraryRepository.save(library);
        log.info("Library successfully created");
        var response = Response.builder()
                .success(true)
                .data(libraryMapper.toResponse(library))
                .error(Empty.builder().build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getLibrary(Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Library not found: " + libraryId));
        log.info("Library successfully found");
        var response = Response.builder()
                .success(true)
                .data(libraryMapper.toResponse(library))
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getAllLibrary(Pageable pageable) {
        Page<LibraryResponse> libraries = libraryRepository.findAll(pageable)
                .map(libraryMapper::toResponse);
        log.info("Library list successfully found");
        Response<Object, Object> response = Response.builder()
                .success(true)
                .data(libraries.getContent())
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> updateLibrary(LibraryRequest libraryRequest, Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Library not found: " + libraryId));
        libraryMapper.update(library, libraryRequest);
        Long authUserId = sessionId.getSessionId();
        if (!libraryRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("AuthUser not found")
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        library.setCreatedBy(libraryRequest.getCreatedBy());
        library.setUpdatedBy(libraryRequest.getUpdatedBy());
        library.setUpdatedAt(libraryRequest.getUpdatedAt());
        libraryRepository.save(library);
        log.info("Library successfully updated");
        var response = Response.builder()
                .success(true)
                .data(libraryMapper.toResponse(library))
                .error(Empty.builder().build())
                .build();
        return ResponseEntity.ok(response);
    }
}
