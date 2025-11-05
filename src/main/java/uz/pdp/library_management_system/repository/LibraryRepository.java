package uz.pdp.library_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.library_management_system.entity.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
}