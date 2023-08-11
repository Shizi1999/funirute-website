package com.poly.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph("graph.product")
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
    @EntityGraph("graph.product")
    List<Product> findAllByIdIn(List<Long> ids);

    @Modifying
    @Transactional
    @Query("Update Product o set o.stock=:stock Where o.id=:id")
    void updateProductStock(@Param("stock") int stock, @Param("id") Long id);
}
