package uz.pdp.library_management_system.mapper.interfaces;

import org.mapstruct.Mapper;
import uz.pdp.library_management_system.entity.Borrowing;
import uz.pdp.library_management_system.request.BorrowingRequest;
import uz.pdp.library_management_system.response.BorrowingResponse;

@Mapper(componentModel = "spring")
public interface BorrowingMapperInterface {

    Borrowing toEntity(BorrowingRequest borrowingRequest);

    BorrowingResponse toResponse(Borrowing borrowing);
}
