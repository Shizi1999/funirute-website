package com.poly.api;

import com.poly.dto.OrderDto;
import com.poly.repository.OrderRepository;
import com.poly.security.CurrentUser;
import com.poly.security.UserPrincipal;
import com.poly.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderApi {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/order")
    public ResponseEntity<?> order(@RequestBody OrderDto dto, @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(orderService.checkout(dto, userPrincipal));
    }

    @GetMapping("/order")
    public ResponseEntity<?> getOrder(@CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(orderService.getOrder((userPrincipal)));
    }

//    @GetMapping("/public/test")
//    public ResponseEntity<?> getOrder() {
//        return ResponseEntity.ok(new APIResponse(orderRepository.findByAccount_Id(1l)));
//    }
}
