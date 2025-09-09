package uz.pdp.library_management_system.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.pdp.library_management_system.entity.Category;

public class CategorySpecification {
    public static Specification<Category> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Category> hasDescription(String description) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("description"), "%" + description + "%");
    }
}
