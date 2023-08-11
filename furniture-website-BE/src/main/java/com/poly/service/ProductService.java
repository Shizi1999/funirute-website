package com.poly.service;

import com.poly.entity.OrderDetail;
import com.poly.entity.Product;
import com.poly.payload.request.ProductQueryParam;
import com.poly.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    APIResponse filterProduct (ProductQueryParam productQueryParam);
    APIResponse create(Product product, MultipartFile image);
    APIResponse update(Product product, MultipartFile image);
    APIResponse delete(Long id);
    APIResponse switchStatus(boolean isAvailable, Long id);
    void updateProductStockAfterCheckout(List<OrderDetail> orderDetails);
}
