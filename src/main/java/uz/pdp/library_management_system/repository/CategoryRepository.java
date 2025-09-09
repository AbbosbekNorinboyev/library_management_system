package uz.pdp.library_management_system.repository;

import uz.pdp.library_management_system.entity.Category;

import java.util.List;

public interface CategoryRepository extends GenericRepository<Category, Long> {
    List<Category> findAllByLibraryId(Long libraryId);
}