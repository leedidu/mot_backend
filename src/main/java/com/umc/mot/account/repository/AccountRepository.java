package com.umc.mot.account.repository;

import com.umc.mot.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    boolean existsByNumber(String number);
}
