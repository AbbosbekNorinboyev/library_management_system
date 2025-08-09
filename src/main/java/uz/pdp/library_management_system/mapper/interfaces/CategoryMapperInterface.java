package uz.pdp.library_management_system.mapper.interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import uz.pdp.library_management_system.dto.request.CategoryRequest;
import uz.pdp.library_management_system.dto.response.CategoryResponse;
import uz.pdp.library_management_system.entity.Category;
import uz.pdp.library_management_system.entity.Library;

@Mapper(componentModel = "spring")
public interface CategoryMapperInterface {
    // source kirib kelayotgan malumot => CategoryRequest
    // target chiquvchi malumot => Category
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "libraryId", target = "library", qualifiedByName = "mapLibrary")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "updatedAt", target = "updatedAt")
    Category toEntity(CategoryRequest categoryRequest);

    @Named("mapLibrary")
    default Library mapLibrary(Long libraryId) {
        if (libraryId == null) {
            return null;
        }
        Library library = new Library();
        library.setId(libraryId);
        return library;
    }

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "library", target = "libraryId", qualifiedByName = "mapLibraryId")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "updatedAt", target = "updatedAt")
    CategoryResponse toResponse(Category category);

    @Named("mapLibraryId")
    default Long mapLibraryId(Library library) {
        return (library != null) ? library.getId() : null;
    }
}
