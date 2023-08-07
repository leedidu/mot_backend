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

    public static void ResponseInfo(){
        String hotel; //호텔이름
        int star; //별점
        int commentCount; //댓글개수
        String hotelImage; //호텔사진
    }
}
