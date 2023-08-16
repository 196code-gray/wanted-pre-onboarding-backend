package com.onboarid.wanted.user.service;

import com.onboarid.wanted.exception.BusinessException;
import com.onboarid.wanted.exception.ExceptionCode;
import com.onboarid.wanted.security.jwt.CustomAuthorityUtils;
import com.onboarid.wanted.user.entity.User;
import com.onboarid.wanted.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service @Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;
    public User savedUser (User user) {
        verifiedEmail(user.getEmail());

        List<String> roles = authorityUtils.createRoles(user.getEmail());

        User saveuser = User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(roles)
                .build();

        return repository.save(saveuser);
    }
    public User verifiedUser (long userId) {
        Optional<User> op = repository.findById(userId);
        User findUser = op.orElseThrow(() -> new BusinessException(ExceptionCode.USER_NOT_FOUND));

        return findUser;
    }
    public void verifiedEmail (String email) {
        Optional<User> op = repository.findByEmail(email);
        if (op.isPresent()) {
            throw new BusinessException(ExceptionCode.USER_EXITS);
        }
    }
    public Long findSecurityContextHolderUserId() {
        Map principal = (Map) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (Long) principal.get("userId");
    }
    public User verifiedSecurityContextHolderUserId () {
        Map principal = (Map) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId =  (Long) principal.get("userId");

        Optional<User> op = repository.findById(userId);
        User findUser = op.orElseThrow(() -> new BusinessException(ExceptionCode.USER_NOT_FOUND));

        return findUser;
    }
}
