package com.poly.speficication;

import com.poly.entity.Category;
import com.poly.payload.request.CategoryQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CategorySpecification {
    public Specification<Category> hasNameLike(String keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + keyword + "%");
    }
    public Specification<Category> getCategorySpecification(CategoryQueryParam categoryQueryParam) {
        Specification<Category> spec = Specification.where(null);
        if (categoryQueryParam.getKeyword() != null) {
            spec = spec.and(hasNameLike(categoryQueryParam.getKeyword()));
        }
        return spec;
    }
}
