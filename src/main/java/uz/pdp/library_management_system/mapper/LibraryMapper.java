package uz.pdp.library_management_system.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.library_management_system.entity.Library;
import uz.pdp.library_management_system.dto.request.LibraryRequest;
import uz.pdp.library_management_system.dto.response.LibraryResponse;

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
}
