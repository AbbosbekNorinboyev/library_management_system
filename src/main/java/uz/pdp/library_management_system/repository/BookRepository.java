package uz.pdp.library_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.library_management_system.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByCategoryId(Long categoryId);
}