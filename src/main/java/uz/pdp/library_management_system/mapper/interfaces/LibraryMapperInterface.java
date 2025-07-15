package uz.pdp.library_management_system.mapper.interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.library_management_system.entity.Library;
import uz.pdp.library_management_system.dto.request.LibraryRequest;
import uz.pdp.library_management_system.dto.response.LibraryResponse;

@Mapper(componentModel = "spring")
public interface LibraryMapperInterface {
    // source kirib kelayotgan malumot => CategoryRequest
    // target chiquvchi malumot => Category
    @Mapping(source = "name", target = "name")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "updatedAt", target = "updatedAt")
    Library toEntity(LibraryRequest libraryRequest);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "updatedAt", target = "updatedAt")
    LibraryResponse toResponse(Library library);
}
