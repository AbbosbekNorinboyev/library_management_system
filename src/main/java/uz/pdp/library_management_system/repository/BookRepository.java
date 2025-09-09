package uz.pdp.library_management_system.repository;

import org.springframework.stereotype.Repository;
import uz.pdp.library_management_system.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends GenericRepository<Book, Long> {
    List<Book> findAllByCategoryId(Long categoryId);
}