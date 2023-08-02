package com.umc.mot.reserve.mapper;

import com.umc.mot.reserve.dto.ReserveRequestDto;
import com.umc.mot.reserve.dto.ReserveResponseDto;
import com.umc.mot.reserve.entity.Reserve;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReserveMapper {
    ReserveResponseDto.Response ReserveToReserveResponseDto(Reserve member);
    Reserve ReserveRequestDtoPostToReserve(ReserveRequestDto.Post post);
    Reserve ReserveRequestDtoPatchToReserve(ReserveRequestDto.Patch patch);
}
