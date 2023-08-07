package com.umc.mot.token.repository;

import com.umc.mot.token.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByLoginId(String loginId);
}
