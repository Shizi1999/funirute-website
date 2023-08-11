package com.poly.mapper;

import com.poly.dto.OrderItemDto;
import com.poly.entity.OrderDetail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapper {
    @Autowired
    ModelMapper modelMapper;

    public OrderDetail toOrderDetail(OrderItemDto orderItemDto){
        return  orderItemDto == null ? null: modelMapper.map(orderItemDto, OrderDetail.class);
    }
}
