package uz.pdp.library_management_system.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.pdp.library_management_system.entity.Book;

public class BookSpecification {
    public static Specification<Book> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Book> hasAuthor(String author) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("author"), "%" + author + "%");
    }

    public static Specification<Book> hasTotalPages(Integer totalPages) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("totalPages"), totalPages);
    }

    public static Specification<Book> hasAvailableCopies(Long availableCopies) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("availableCopies"), availableCopies);
    }
}
