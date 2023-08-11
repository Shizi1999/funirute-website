package com.poly.service;

import com.poly.entity.Account;
import com.poly.payload.response.APIResponse;
import com.poly.security.UserPrincipal;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    APIResponse login(Account account);
    APIResponse register(Account account);
    APIResponse register (Account account, MultipartFile file);
    APIResponse updateInformation (UserPrincipal userPrincipal,Account account, MultipartFile file);
    APIResponse logout();
}
