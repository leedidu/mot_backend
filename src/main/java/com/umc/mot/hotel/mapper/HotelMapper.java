package com.umc.mot.hotel.mapper;

import com.umc.mot.hotel.dto.HotelRequestDto;
import com.umc.mot.hotel.dto.HotelResponseDto;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.message.dto.MessageRequestDto;
import com.umc.mot.message.dto.MessageResponseDto;
import com.umc.mot.message.entity.Message;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    HotelResponseDto.Response HotelToHotelResponseDto(Hotel hotel);
    List<HotelResponseDto.Response> HotelToListHotelResponseDto(List<Hotel> hotel);

    Hotel HotelRequestDtoPostToHotel(HotelRequestDto.Post post);


    Hotel HotelRequestDtoPatchToHotel (HotelRequestDto.Patch patch);
}
