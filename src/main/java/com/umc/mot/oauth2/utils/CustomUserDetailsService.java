package com.umc.mot.oauth2.utils;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.token.entity.Token;
import com.umc.mot.token.repository.TokenRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.umc.mot.exception.ExceptionCode;

import java.util.Collection;
import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final TokenRepository tokenRepository;
    private final CustomAuthorityUtils authorityUtils;

    public CustomUserDetailsService(TokenRepository tokenRepository,
                                    CustomAuthorityUtils authorityUtils) {
        this.tokenRepository = tokenRepository;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Token> optionalMember = tokenRepository.findByLoginId(loginId);
        Token findToken = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.TOKEN_MEMBER_NOT_FOUND));

        return new CustomUserDetails(findToken);
    }


    private final class CustomUserDetails extends Token implements UserDetails {
        CustomUserDetails(Token token) {
            setId(token.getId());
            setAccessToken(token.getAccessToken());
            setRefreshToken(token.getRefreshToken());
            setLoginId(token.getLoginId());
            setLoginPw(token.getPassword());
            setPurchaseMember(token.getPurchaseMember());
            setSellMember(token.getSellMember());
            setRoles(token.getRoles());

        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorityUtils.createAuthorities(this.getRoles());
        }

        @Override
        public String getUsername() {
            return getLoginId();
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