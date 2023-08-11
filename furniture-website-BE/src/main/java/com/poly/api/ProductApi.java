package com.poly.api;

import com.poly.entity.Product;
import com.poly.payload.request.ProductQueryParam;
import com.poly.payload.response.APIResponse;
import com.poly.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
public class ProductApi {
    @Autowired
    ProductService productService;

    @GetMapping("/public/products/filter")
    public ResponseEntity<?> filterProduct(ProductQueryParam productQueryParam) {
        return ResponseEntity.ok(productService.filterProduct(productQueryParam));
    }

    @PostMapping("/admin/products")
    public ResponseEntity<?> createProduct(
            @RequestPart(name = "product") Product product,
            @RequestPart(name = "image") MultipartFile image) {
        APIResponse response = productService.create(product, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/admin/products")
    public ResponseEntity<?> updateProduct(
            @RequestPart(name = "product") Product product,
            @RequestPart(name = "image") MultipartFile image) {
        APIResponse response = productService.update(product, image);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        APIResponse response = productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/admin/product-status")
    public ResponseEntity<?> switchProductStatus(
            @RequestBody Product product) {
        APIResponse response = productService.switchStatus(product.getIsAvailable(), product.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
