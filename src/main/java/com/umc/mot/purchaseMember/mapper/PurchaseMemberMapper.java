package com.umc.mot.purchaseMember.mapper;

import com.umc.mot.purchaseMember.dto.PurchaseMemberRequestDto;
import com.umc.mot.purchaseMember.dto.PurchaseMemberResponseDto;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PurchaseMemberMapper {
    PurchaseMemberResponseDto.Response purchaseMemberToPurchaseMemberResponseDto(PurchaseMember member);
    PurchaseMember purchaseMemberRequestDtoPostToPurchaseMember(PurchaseMemberRequestDto.Post post);
    PurchaseMember purchaseMemberRequestDtoPatchToPurchaseMember(PurchaseMemberRequestDto.Patch patch);
}
