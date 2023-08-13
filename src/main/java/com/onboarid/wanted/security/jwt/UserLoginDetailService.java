package com.onboarid.wanted.security.jwt;

import com.onboarid.wanted.exception.BusinessException;
import com.onboarid.wanted.exception.ExceptionCode;
import com.onboarid.wanted.user.entity.User;
import com.onboarid.wanted.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component @RequiredArgsConstructor
public class UserLoginDetailService implements UserDetailsService {
    private final UserRepository repository;
    private final CustomAuthorityUtils utils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> op = repository.findByEmail(username);
        User find = op.orElseThrow(() -> new BusinessException(ExceptionCode.USER_NOT_FOUND));

        return new UserDetail(find);
    }

    public class UserDetail extends User implements UserDetails {
        UserDetail(User user) {
            setUserId(user.getUserId());
            setEmail(user.getEmail());
            setPassword(user.getPassword());
            setRoles(user.getRoles());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return utils.createAuthorities(this.getRoles());
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

}
