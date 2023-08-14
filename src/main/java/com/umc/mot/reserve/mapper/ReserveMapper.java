package com.umc.mot.reserve.mapper;

import com.umc.mot.category.dto.CategoryResponseDto;
import com.umc.mot.category.entity.Category;
import com.umc.mot.reserve.dto.ReserveRequestDto;
import com.umc.mot.reserve.dto.ReserveResponseDto;
import com.umc.mot.reserve.entity.Reserve;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReserveMapper {
    ReserveResponseDto.Response ReserveToReserveResponseDto(Reserve member);
    Reserve ReserveRequestDtoPostToReserve(ReserveRequestDto.Post post);
    Reserve ReserveRequestDtoPatchToReserve(ReserveRequestDto.Patch patch);
    List<ReserveResponseDto.Get> ReservesResponseDto(List<Reserve> reserves);

}
