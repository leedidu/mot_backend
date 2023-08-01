package com.umc.mot.sellMember.mapper;
import com.umc.mot.sellMember.dto.SellMemberRequestDto;
import com.umc.mot.sellMember.dto.SellMemberResponseDto;
import com.umc.mot.sellMember.entity.SellMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SellMemberMapper {
    SellMemberResponseDto.Response SellMemberToSellMemberDtoResponse(SellMember member);
    SellMember SellMemberRequestDtoPostToSellMember(SellMemberRequestDto.Post post);
    SellMember SellMemberRequestDtoPatchToSellMember(SellMemberRequestDto.Patch patch);

}
