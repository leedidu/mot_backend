package com.umc.mot.account.mapper;

import com.umc.mot.account.dto.AccountResponseDto;
import com.umc.mot.account.dto.AccountRequestDto;
import com.umc.mot.account.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
        AccountResponseDto.Response AccountToAccountResponseDto(Account account);
        Account AccountRequestDtoPostToAccount(AccountRequestDto.Post post);
        Account AccountRequestDtoPatchToAccount(AccountRequestDto.Patch patch);

}


