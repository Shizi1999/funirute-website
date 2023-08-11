package com.poly.utils;

import org.springframework.data.domain.Page;
import java.util.HashMap;
import java.util.Map;

public class PageUtils {
    public static <T> Map<String, Object> toPageResponse(Page<T> page) {
        Map<String, Object> res = new HashMap<>();
        res.put("datas", page.getContent());
        res.put("currentPage", page.getNumber());
        res.put("totalItems", page.getTotalElements());
        res.put("totalPage", page.getTotalPages());
        res.put("pageSize", page.getSize());
        return res;
    }
}
