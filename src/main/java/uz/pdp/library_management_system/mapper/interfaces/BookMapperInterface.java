package uz.pdp.library_management_system.mapper.interfaces;

import org.mapstruct.Mapper;
import uz.pdp.library_management_system.entity.Book;
import uz.pdp.library_management_system.dto.request.BookRequest;

@Mapper(componentModel = "spring")
public interface BookMapperInterface {
    Book toEntity(BookRequest bookRequest);

    BookRequest toResponse(Book book);
}
