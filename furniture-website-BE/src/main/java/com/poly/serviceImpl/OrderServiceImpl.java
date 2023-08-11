package com.poly.serviceImpl;

import com.poly.dto.OrderDto;
import com.poly.dto.OrderItemDto;
import com.poly.entity.Order;
import com.poly.entity.OrderDetail;
import com.poly.entity.Product;
import com.poly.mapper.OrderMapper;
import com.poly.payload.response.APIResponse;
import com.poly.payload.response.FailureAPIResponse;
import com.poly.repository.OrderRepository;
import com.poly.repository.ProductRepository;
import com.poly.security.UserPrincipal;
import com.poly.service.OrderService;
import com.poly.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    @Override
    @Transactional(transactionManager = "transactionManager")
    public APIResponse checkout(OrderDto dto, UserPrincipal userPrincipal) {
        if (checkInventory(dto)) {
            Order order = orderMapper.toOrder(dto);
            setTotalQuantityAndTotalPayment(order);
            order.setAddress(dto.getAddress());
            order.setAccount(userPrincipal.toAccount());
            orderRepository.save(order);
            productService.updateProductStockAfterCheckout(order.getOrderDetailList());
            return new APIResponse("Save order successful");
        } else {
            return new FailureAPIResponse("San pham vua het hang");
        }
    }

    private void setTotalQuantityAndTotalPayment(Order order) {
        double totalPayment = 0;
        int totalQuantity = 0;
        List<OrderDetail> orderDetails = order.getOrderDetailList();
        for(OrderDetail orderDetail: orderDetails){
            totalPayment += orderDetail.getQuantity() * orderDetail.getProduct().getPrice();
            totalQuantity += orderDetail.getQuantity();
            orderDetail.setOrder(order);
        }
        order.setTotalQuantity(totalQuantity);
        order.setTotalPrice(totalPayment);
    }

    private boolean checkInventory(OrderDto dto) {
        List<OrderItemDto> dtos = dto.getOrderItemDtos();
        List<Long> productIds = dtos.stream().map(orderItemDto ->
                orderItemDto.getProduct().getId()).collect(Collectors.toList());
        List<Product> products = productRepository.findAllByIdIn(productIds);
        boolean flag = true;
        for (OrderItemDto orderItemDto : dtos) {
            Product product = findProductByIdInList(orderItemDto.getProduct().getId(), products);
            orderItemDto.setProduct(product);
            if (orderItemDto.getQuantity() > product.getStock()) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private Product findProductByIdInList(Long id, List<Product> products) {
        Product product = null;
        for (Product pro : products) {
            if (pro.getId().equals(id)) {
                product = pro;
                break;
            }
        }
        return product;
    }

    @Override
    public APIResponse getOrder(UserPrincipal userPrincipal) {
        return new APIResponse(orderRepository.findByAccount(userPrincipal.toAccount()));
    }
}
