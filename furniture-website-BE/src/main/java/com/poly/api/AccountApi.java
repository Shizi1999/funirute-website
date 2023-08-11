package com.poly.api;

import com.poly.payload.request.AccountQueryParam;
import com.poly.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountApi {
    @Autowired
    AccountService accountService;

    @GetMapping("/admin/accounts")
    public ResponseEntity<?> getAllAccount (AccountQueryParam accountQueryParam) {
        return ResponseEntity.ok(accountService.filterAccount(accountQueryParam));
    }

    @PutMapping("/admin/accounts/{id}")
    public ResponseEntity<?> blockAccount (@PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.blockAccount(id));
    }

    @DeleteMapping("/admin/accounts/{id}")
    public ResponseEntity<?> remove (@PathVariable("id") Long id){
        return ResponseEntity.ok(accountService.removeAccount(id));
    }
}
