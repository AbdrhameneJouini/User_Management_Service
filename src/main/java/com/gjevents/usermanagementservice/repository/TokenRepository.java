package com.gjevents.usermanagementservice.repository;

import com.gjevents.usermanagementservice.model.Token;
import com.gjevents.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {


    Token findByUserAndTokenType(User user, String passwordReset);

    Token findByValue(String token);

    Token findByValueAndTokenType(String token, String emailVerification);



}