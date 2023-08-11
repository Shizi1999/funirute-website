package com.poly.mapper;

import com.poly.dto.AccountDto;
import com.poly.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    @Autowired
    ModelMapper modelMapper;

    public Account toAccount(AccountDto dto) {
        return dto == null ? null : modelMapper.map(dto, Account.class);
    }

    public AccountDto accountDto(Account account) {
        return account == null ? null : modelMapper.map(account, AccountDto.class);
    }
}
