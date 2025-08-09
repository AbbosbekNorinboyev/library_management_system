package uz.pdp.library_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.dto.request.LibraryRequest;
import uz.pdp.library_management_system.dto.response.LibraryResponse;
import uz.pdp.library_management_system.entity.Library;

@Component
public class LibraryMapper {
    public Library toEntity(LibraryRequest libraryRequest) {
        return Library.builder()
                .name(libraryRequest.getName())
                .address(libraryRequest.getAddress())
                .phone(libraryRequest.getPhone())
                .email(libraryRequest.getEmail())
                .createdBy(libraryRequest.getCreatedBy())
                .createdAt(libraryRequest.getCreatedAt())
                .updatedBy(libraryRequest.getUpdatedBy())
                .updatedAt(libraryRequest.getUpdatedAt())
                .build();
    }

    public LibraryResponse toResponse(Library library) {
        return LibraryResponse.builder()
                .id(library.getId())
                .name(library.getName())
                .address(library.getAddress())
                .phone(library.getPhone())
                .email(library.getEmail())
                .createdBy(library.getCreatedBy())
                .createdAt(library.getCreatedAt())
                .updatedBy(library.getUpdatedBy())
                .updatedAt(library.getUpdatedAt())
                .build();
    }

    public void update(Library entity, LibraryRequest request) {
        if (request == null) {
            return;
        }
        if (request.getName() != null && request.getName().trim().isEmpty()) {
            entity.setName(request.getName());
        }
        if (request.getAddress() != null && request.getAddress().trim().isEmpty()) {
            entity.setAddress(request.getAddress());
        }
        if (request.getEmail() != null && request.getEmail().trim().isEmpty()) {
            entity.setEmail(request.getEmail());
        }
    }
}
