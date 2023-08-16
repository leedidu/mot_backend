package com.umc.mot.account.service;


import com.umc.mot.account.entity.Account;
import com.umc.mot.account.repository.AccountRepository;
import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.sellMember.entity.SellMember;
import com.umc.mot.token.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {


    private final AccountRepository accountRepository;
    private final TokenService tokenService;

    //Create
    public Account createAccount(Account account) {
        SellMember sellM = tokenService.getLoginSellMember();
        if (accountRepository.existsByNumber(account.getNumber())) {
            throw new IllegalArgumentException("이미 존재하는 계좌입니다. 생성이 불가능합니다.");
        }

        account.setSellMember(sellM);
        return accountRepository.save(account);
    }

    // Read
    public Account findAccount(int accountId) {
        Account account = verifiedAccount(accountId);
        return account;
    }


    // Update
    public Account patchAccount(Account account) {
        SellMember sellM = tokenService.getLoginSellMember();
        Account findAccount = verifiedAccount(account.getId());
        Optional.ofNullable(account.getId()).ifPresent(findAccount::setId);
        Optional.ofNullable(account.getName()).ifPresent(findAccount::setName);
        Optional.ofNullable(account.getBank()).ifPresent(findAccount::setBank);
        if (accountRepository.existsByNumber(account.getNumber())) {
            throw new IllegalArgumentException("이미 존재하는 계좌입니다. 생성이 불가능합니다.");
        }
        else{
            Optional.ofNullable(account.getNumber()).ifPresent(findAccount::setNumber);
        }


        return accountRepository.save(findAccount);
    }

    // Delete
    public void deleteAccount(int accountId) {
        SellMember sellM = tokenService.getLoginSellMember();
        Account account = verifiedAccount(accountId);
        accountRepository.delete(account);
    }

    // 멤버 검증
    public Account verifiedAccount(int accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        return account.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));

    }



}
