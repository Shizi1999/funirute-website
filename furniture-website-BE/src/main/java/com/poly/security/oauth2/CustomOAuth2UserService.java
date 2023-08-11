package com.poly.security.oauth2;

import com.poly.entity.Account;
import com.poly.exception.OAuth2AuthenticationProcessingException;
import com.poly.repository.AccountRepository;
import com.poly.security.UserPrincipal;
import com.poly.security.oauth2.user.OAuth2UserInfo;
import com.poly.security.oauth2.user.OAuth2UserInfoFactory;
import com.poly.type.EAuthProvider;
import com.poly.type.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Transactional()
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            oAuth2User = processOAuth2User(oAuth2UserRequest, oAuth2User);
            return oAuth2User;
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    public OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Không thể xác thực email!");
        }
        Optional<Account> optionalAccount = accountRepository.findByEmail(oAuth2UserInfo.getEmail());
        Account acc;
        if (optionalAccount.isPresent()) {
            acc = optionalAccount.get();
            if (!acc.getProvider().equals(EAuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Nền tảng đăng nhập không hợp lệ");
            }
            acc = updateExistingUser(acc, oAuth2UserInfo);
        } else {
            acc = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return UserPrincipal.create(acc, oAuth2User.getAttributes());
    }

    @Transactional()
    public Account registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        Account account = new Account();
        account.setRole(ERole.ROLE_USER);
        account.setProvider(EAuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        account.setProviderId(oAuth2UserInfo.getId());
        account.setEmail(oAuth2UserInfo.getEmail());
        account.setAvatar(oAuth2UserInfo.getImageUrl());
        account.setIsActivated(true);
        return  accountRepository.save(account);
    }

    @Transactional(transactionManager = "transactionManager")
    public Account updateExistingUser(Account existingAccount, OAuth2UserInfo oAuth2UserInfo) {
        if (existingAccount.getAvatar() == null) {
            existingAccount.setAvatar(oAuth2UserInfo.getImageUrl());
            return accountRepository.save(existingAccount);
        }
        return existingAccount;
    }

}
