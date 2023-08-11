package com.poly.api;


import com.poly.entity.Account;
import com.poly.payload.response.APIResponse;
import com.poly.security.CurrentUser;
import com.poly.security.UserPrincipal;
import com.poly.service.AuthService;
import com.poly.serviceImpl.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class AuthApi {
    @Autowired
    AuthService authService;
    @Autowired
    CloudinaryService cloudinaryService;

    @PostMapping(path = "/auth/register")
    public ResponseEntity<?> registerWithoutAvatar(
            @RequestBody Account account) {
        APIResponse response = authService.register(account);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/auth/registerWithAvatar", consumes = {"multipart/form-data"})
    public ResponseEntity<?> registerWithAvatar(
            @RequestPart(name = "account") Account account,
            @RequestPart(name = "file") MultipartFile file) {
        APIResponse response = authService.register(account, file);
        return ResponseEntity.ok(response);
    }


    @PutMapping(path = "/account-information", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateInformation(
            @RequestPart(name = "account") Account account,
            @RequestPart(name = "file") @Nullable MultipartFile file,
            @CurrentUser UserPrincipal userPrincipal) {
        APIResponse response = authService.updateInformation(userPrincipal, account, file);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        APIResponse response = authService.login(account);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/auth/logout")
    public ResponseEntity<?> logout() {
        APIResponse response = authService.logout();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok("null");
    }


    @GetMapping(path = "/auth/test")
    public ResponseEntity<?> Test() {
        cloudinaryService.deleteFile("User/atgcy97cka33r1abw9dq");
        return ResponseEntity.ok(new APIResponse("success"));
    }
}
