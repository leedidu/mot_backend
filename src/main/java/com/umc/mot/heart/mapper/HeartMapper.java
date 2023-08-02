package com.umc.mot.heart.mapper;

import com.umc.mot.heart.dto.HeartRequestDto;
import com.umc.mot.heart.dto.HeartResponseDto;
import com.umc.mot.heart.entity.Heart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HeartMapper {

    HeartResponseDto.Response HeartToHeartResponseDto(Heart heart);

    Heart HeartRequestDtoPostToHeart(HeartRequestDto.Post post);

    Heart HeartRequestDtoPatchToHeart(HeartRequestDto.Patch patch);



}
