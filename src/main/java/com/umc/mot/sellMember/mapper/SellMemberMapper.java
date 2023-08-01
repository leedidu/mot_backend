package com.umc.mot.sellMember.mapper;
import com.umc.mot.sellMember.dto.SellMemberRequestDto;
import com.umc.mot.sellMember.dto.SellMemberResponseDto;
import com.umc.mot.sellMember.entity.SellMember;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")
public interface SellMemberMapper {
    SellMemberResponseDto.Response SellMemberToSellMemberResponseDto(SellMember member);
    SellMember SellMemberRequestDtoPostToSellMember(SellMemberRequestDto.Post post);
    SellMember SellMemberRequestDtoPatchToSellMember(SellMemberRequestDto.Patch patch);

}
