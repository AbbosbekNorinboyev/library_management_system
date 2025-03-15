package uz.pdp.library_management_system.mapper.interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.library_management_system.entity.Category;
import uz.pdp.library_management_system.request.CategoryRequest;
import uz.pdp.library_management_system.response.CategoryResponse;

@Mapper(componentModel = "spring")
public interface CategoryMapperInterface {
    // source kirib kelayotgan malumot => CategoryRequest
    // target chiquvchi malumot => Category
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "updatedAt", target = "updatedAt")
    Category toEntity(CategoryRequest categoryRequest);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "updatedAt", target = "updatedAt")
    CategoryResponse toResponse(Category category);
}
