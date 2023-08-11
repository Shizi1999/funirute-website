package com.poly.service;

import com.poly.entity.Category;
import com.poly.payload.request.CategoryQueryParam;
import com.poly.payload.response.APIResponse;

public interface CategoryService {
    APIResponse filterCategory (CategoryQueryParam categoryQueryParam);
    APIResponse create(Category category);
    APIResponse update(Category category);
    APIResponse delete(Long id);

    APIResponse getExistCategory();
}
