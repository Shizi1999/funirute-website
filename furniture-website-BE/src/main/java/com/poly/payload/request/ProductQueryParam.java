package com.poly.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductQueryParam extends BaseQueryRequest {
    Integer minPrice;
    Integer maxPrice;
    Integer categoryId;
    String keyword;
    Boolean available;
}
