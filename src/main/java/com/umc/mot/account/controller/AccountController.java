package com.umc.mot.account.controller;

import com.umc.mot.account.dto.AccountRequestDto;
import com.umc.mot.account.dto.AccountResponseDto;
import com.umc.mot.account.entity.Account;
import com.umc.mot.account.mapper.AccountMapper;
import com.umc.mot.account.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/account")
@Validated
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    // Create
    @PostMapping
    public ResponseEntity postAccount(@Valid @RequestBody AccountRequestDto.Post post){
        Account account = accountService.createAccount(accountMapper.AccountRequestDtoPostToAccount(post));
        AccountResponseDto.Response response=accountMapper.AccountToAccountResponseDto(account);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // Read
    @GetMapping
    public ResponseEntity getAccount(@Positive @RequestParam int accountId){
        Account account = accountService.findAccount(accountId);
        AccountResponseDto.Response response = accountMapper.AccountToAccountResponseDto(account);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    // Update
    @PatchMapping("/{account-id}")
    public ResponseEntity patchAccount(@Positive @PathVariable("account-id") int accountId,
                                       @RequestBody AccountRequestDto.Patch patch) {
        patch.setId(accountId);
        Account account = accountService.patchAccount(accountMapper.AccountRequestDtoPatchToAccount(patch));
        AccountResponseDto.Response response =accountMapper.AccountToAccountResponseDto(account);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Delete
    @DeleteMapping("/{account-id}")
    public ResponseEntity deleteAccount(@Positive @PathVariable("account-id") int accountId) {
        accountService.deleteAccount(accountId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
