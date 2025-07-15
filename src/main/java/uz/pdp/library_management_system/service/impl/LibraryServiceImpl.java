package uz.pdp.library_management_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.library_management_system.config.SessionId;
import uz.pdp.library_management_system.dto.ErrorDTO;
import uz.pdp.library_management_system.dto.request.LibraryRequest;
import uz.pdp.library_management_system.dto.response.Response;
import uz.pdp.library_management_system.entity.Library;
import uz.pdp.library_management_system.exception.CustomException;
import uz.pdp.library_management_system.mapper.LibraryMapper;
import uz.pdp.library_management_system.repository.LibraryRepository;
import uz.pdp.library_management_system.service.LibraryService;
import uz.pdp.library_management_system.util.validation.LibraryValidation;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryServiceImpl implements LibraryService {
    private final LibraryMapper libraryMapper;
    private final LibraryRepository libraryRepository;
    private final LibraryValidation libraryValidation;
    private final SessionId sessionId;

    @Override
    public Response createLibrary(LibraryRequest libraryRequest) {
        List<ErrorDTO> errors = libraryValidation.validate(libraryRequest);
        if (!errors.isEmpty()) {
            return Response.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Library validation error")
                    .success(false)
                    .error(errors)
                    .build();
        }
        Long authUserId = sessionId.getSessionId();
        if (!libraryRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            return Response.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("AuthUser not found")
                    .success(false)
                    .build();
        }
        Library library = libraryMapper.toEntity(libraryRequest);
        libraryRepository.save(library);
        log.info("Library successfully created");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Library successfully created")
                .success(true)
                .data(libraryMapper.toResponse(library))
                .build();
    }

    @Override
    public Response getLibrary(Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Library not found: " + libraryId));
        log.info("Library successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Library successfully found")
                .success(true)
                .data(libraryMapper.toResponse(library))
                .build();
    }

    @Override
    public Response getAllLibrary() {
        List<Library> libraries = libraryRepository.findAll();
        log.info("Library list successfully found");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Library list successfully found")
                .success(true)
                .data(libraries.stream().map(libraryMapper::toResponse).toList())
                .build();
    }

    @Override
    public Response updateLibrary(LibraryRequest libraryRequest, Long libraryId) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Library not found: " + libraryId));
        library.setName(libraryRequest.getName());
        library.setAddress(libraryRequest.getAddress());
        library.setEmail(libraryRequest.getEmail());
        Long authUserId = sessionId.getSessionId();
        if (!libraryRequest.getCreatedBy().equals(authUserId)) {
            log.error("AuthUser not found");
            return Response.builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("AUthUser not found")
                    .success(false)
                    .build();
        }
        library.setCreatedBy(libraryRequest.getCreatedBy());
        library.setUpdatedBy(libraryRequest.getUpdatedBy());
        library.setUpdatedAt(libraryRequest.getUpdatedAt());
        libraryRepository.save(library);
        log.info("Library successfully updated");
        return Response.builder()
                .code(HttpStatus.OK.value())
                .message("Library successfully updated")
                .success(true)
                .build();
    }
}
