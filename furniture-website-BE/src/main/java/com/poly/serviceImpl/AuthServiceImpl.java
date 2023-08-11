package com.poly.serviceImpl;

import com.poly.entity.Account;
import com.poly.mapper.AccountMapper;
import com.poly.payload.response.APIResponse;
import com.poly.payload.response.AuthResponse;
import com.poly.payload.response.CloudinaryResponse;
import com.poly.repository.AccountRepository;
import com.poly.security.TokenProvider;
import com.poly.security.UserPrincipal;
import com.poly.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    CloudinaryService cloudinaryService;

    @Override
    public APIResponse login(Account account) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(account.getEmail(), account.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Account acc = accountRepository.findByEmail(account.getEmail()).orElse(null);
            if(acc == null){
                return APIResponse.builder().message("Username or password is incorrect").success(false).build();
            }
            if(acc.getIsActivated() == false) {
                return APIResponse.builder().message("Account has been blocked").success(false).build();
            }

            String token = tokenProvider.generateToken(acc);
            AuthResponse authResponse = new AuthResponse(token, accountMapper.accountDto(acc));
            return  APIResponse.builder().message("Success").success(true).data(authResponse).build();
        }catch (Exception ex){
            return APIResponse.builder().message("Username or password is incorrect").success(false).build();
        }
    }
    @Override
    public APIResponse logout() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(null);
                return APIResponse.builder().message("Logout successful").success(true).build();
            } else {
                return APIResponse.builder().message("User not logged in").success(false).build();
            }
        } catch (Exception ex) {
            return APIResponse.builder().message("Logout failed").success(false).build();
        }
    }

    @Override
    public APIResponse register(Account account) {
        Account exists =  accountRepository.findByEmail(account.getEmail()).orElse(null);
        if(exists != null){
            return APIResponse.builder().message("Email is exists").success(false).build();
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return APIResponse.builder().message("Register successfully!").success(true).build();
    }

    @Override
    public APIResponse register(Account account, MultipartFile file) {
        Account exists =  accountRepository.findByEmail(account.getEmail()).orElse(null);
        if(exists != null){
            return APIResponse.builder().message("Email is exists").success(false).build();
        }
        CloudinaryResponse response = cloudinaryService.uploadFile(file, "User");
        String encoderPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encoderPassword);
        account.setCloudinaryId(response.getCloudinaryId());
        account.setAvatar(response.getUrl());
        accountRepository.save(account);
        return APIResponse.builder().message("Register successfully!").success(true).build();
    }

    @Override
    public APIResponse updateInformation(UserPrincipal userPrincipal, Account account, MultipartFile file) {
        Account exists = accountRepository.findById(userPrincipal.getId()).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });
        exists.setIsActivated(account.getIsActivated());
        if(file != null){
            if(exists.getCloudinaryId() != null){
                cloudinaryService.deleteFile(exists.getCloudinaryId());
            }
            CloudinaryResponse response = cloudinaryService.uploadFile(file, "User");
            exists.setCloudinaryId(response.getCloudinaryId());
            exists.setAvatar(response.getUrl());
        }
        exists = accountRepository.save(exists);
        return APIResponse.builder().message("update successful").success(true).data(exists).build();
    }


}
