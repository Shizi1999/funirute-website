package com.poly.serviceImpl;

import com.poly.entity.Category;
import com.poly.payload.request.CategoryQueryParam;
import com.poly.payload.response.APIResponse;
import com.poly.payload.response.FailureAPIResponse;
import com.poly.repository.CategoryRepository;
import com.poly.service.CategoryService;
import com.poly.speficication.CategorySpecification;
import com.poly.utils.PageUtils;
import com.poly.utils.RequestParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategorySpecification categorySpecification;

    @Override
    public APIResponse filterCategory(CategoryQueryParam categoryQueryParam) {
        Specification<Category> spec = categorySpecification.getCategorySpecification(categoryQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(categoryQueryParam);
        Page<Category> response = categoryRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
    }

    @Override
    public APIResponse create(Category category) {
        categoryRepository.save(category);
        return new APIResponse("Create Category Successful");
    }

    @Override
    public APIResponse update(Category category) {
        categoryRepository.save(category);
        return new APIResponse("Update Category Successful");
    }

    @Override
    public APIResponse delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
        return new APIResponse("Delete Category Successful");
    }

    @Override
    public APIResponse getExistCategory() {
        return new APIResponse(categoryRepository.getExistCategory());
    }
}
