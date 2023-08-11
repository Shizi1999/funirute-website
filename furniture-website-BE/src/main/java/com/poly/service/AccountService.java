package com.poly.service;

import com.poly.payload.request.AccountQueryParam;
import com.poly.payload.response.APIResponse;

public interface AccountService {
    APIResponse filterAccount(AccountQueryParam accountQueryParam);

    APIResponse blockAccount(Long id);

    APIResponse removeAccount(Long id);
}
