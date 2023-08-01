package com.umc.mot.sellMember.mapper;
import com.umc.mot.sellMember.dto.SellMemberRequestDto;
import com.umc.mot.sellMember.dto.SellMemberResponseDto;
import com.umc.mot.sellMember.entity.SellMember;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface SellMemberMapper {
    SellMemberResponseDto.Response sellMemberToSellMemberResponseDto(SellMember member);
    SellMember sellMemberRequestDtoPostToSellMember(SellMemberRequestDto.Post post);
    SellMember sellMemberRequestDtoPatchToSellMember(SellMemberRequestDto.Patch patch);
}
