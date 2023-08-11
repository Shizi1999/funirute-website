package com.poly.serviceImpl;

import com.poly.entity.Account;
import com.poly.exception.EntityNotFountException;
import com.poly.payload.request.AccountQueryParam;
import com.poly.payload.response.APIResponse;
import com.poly.payload.response.SuccessAPIResponse;
import com.poly.repository.AccountRepository;
import com.poly.service.AccountService;
import com.poly.speficication.AccountSpecification;
import com.poly.utils.PageUtils;
import com.poly.utils.RequestParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountSpecification accountSpecification;

    @Autowired
    RequestParamsUtils requestParamsUtils;

    @Override
    public APIResponse filterAccount(AccountQueryParam accountQueryParam) {
        Specification<Account> spec = accountSpecification.getAccountSpecification(accountQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(accountQueryParam);
        Page<Account> response = accountRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
    }

    @Override
    public APIResponse blockAccount(Long id) {
        Account  account = accountRepository.findById(id).get();
        account.setIsActivated(false);
        accountRepository.save(account);
        return new SuccessAPIResponse("Block account successful");
    }

    @Override
    public APIResponse removeAccount(Long id) {
        accountRepository.deleteById(id);
        return new SuccessAPIResponse("Remove account successful");
    }
}
