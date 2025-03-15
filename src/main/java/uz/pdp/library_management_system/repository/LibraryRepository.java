package uz.pdp.library_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.library_management_system.entity.Library;

public interface LibraryRepository extends JpaRepository<Library, Long> {
}