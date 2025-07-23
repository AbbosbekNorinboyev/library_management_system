package uz.pdp.library_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.library_management_system.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByLibraryId(Long libraryId);
}