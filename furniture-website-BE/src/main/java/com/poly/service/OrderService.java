package com.poly.service;

import com.poly.dto.OrderDto;
import com.poly.payload.response.APIResponse;
import com.poly.security.UserPrincipal;

public interface OrderService {
    APIResponse checkout(OrderDto dto, UserPrincipal userPrincipal);
    APIResponse getOrder(UserPrincipal userPrincipal);
}
