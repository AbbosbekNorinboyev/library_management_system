package uz.pdp.library_management_system.repository;

import org.springframework.stereotype.Repository;
import uz.pdp.library_management_system.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends GenericRepository<Category, Long> {
    List<Category> findAllByLibraryId(Long libraryId);
}