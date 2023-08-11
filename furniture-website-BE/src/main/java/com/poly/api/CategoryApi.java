package com.poly.api;

import com.poly.entity.Category;
import com.poly.payload.request.CategoryQueryParam;
import com.poly.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryApi {
    @Autowired
    CategoryService categoryService;

    @GetMapping("public/categories/filter")
    public ResponseEntity<?> filterCategory(CategoryQueryParam categoryQueryParam) {
        return ResponseEntity.ok(categoryService.filterCategory(categoryQueryParam));
    }

    @GetMapping("/public/categories-exist")
    public ResponseEntity<?> getExistCategory() {
        return ResponseEntity.ok(categoryService.getExistCategory());
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.create(category));
    }

    @PutMapping("/admin/categories")
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.update(category));
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }
}


