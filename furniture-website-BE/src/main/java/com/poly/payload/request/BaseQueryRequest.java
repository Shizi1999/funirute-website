package com.poly.payload.request;

import lombok.Data;

@Data
public class BaseQueryRequest {
    int page = 0;
    int pageSize = 16;
    String orderBy = "asc";
    String sortFiled = "id";
}
