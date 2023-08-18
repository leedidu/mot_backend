package com.umc.mot.heart.mapper;

import com.umc.mot.heart.dto.HeartRequestDto;
import com.umc.mot.heart.dto.HeartResponseDto;
import com.umc.mot.heart.entity.Heart;
import com.umc.mot.hotel.entity.Hotel;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface HeartMapper {

    HeartResponseDto.Response HeartToHeartResponseDto(Heart heart, int HotelId, int PurchaseMemberId);

    default List<HeartResponseDto.ListResponse> HeartToResponse(List<Heart> heart, List<Hotel> hotel){

        List<HeartResponseDto.ListResponse> listResponses = new ArrayList<>();
        for(int i=0;i<heart.size();i++){
            HeartResponseDto.ListResponse response = new HeartResponseDto.ListResponse();
            response.setStar(hotel.get(i).getStar());
            response.setId(heart.get(i).getId());
            response.setName(hotel.get(i).getName());
            response.setCommentCount(hotel.get(i).getCommentCount());
            response.setPrice(hotel.get(i).getPrice());
            response.setPhoto(hotel.get(i).getPhoto());
            listResponses.add(response);

        }
        return listResponses;

    }

    Heart HeartRequestDtoPostToHeart(HeartRequestDto.Post post);

    Heart HeartRequestDtoPatchToHeart(HeartRequestDto.Patch patch);



}
