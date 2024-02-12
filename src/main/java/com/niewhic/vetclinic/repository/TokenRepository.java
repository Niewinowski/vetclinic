package com.niewhic.vetclinic.repository;

import com.niewhic.vetclinic.model.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);
}
