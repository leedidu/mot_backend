package com.umc.mot.message.mapper;

import com.umc.mot.message.dto.MessageRequestDto;
import com.umc.mot.message.dto.MessageResponseDto;
import com.umc.mot.message.entity.Message;
import com.umc.mot.packagee.dto.PackageRequestDto;
import com.umc.mot.packagee.dto.PackageResponseDto;
import com.umc.mot.packagee.entity.Package;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageResponseDto.Response MessageToMessageResponseDto(Message message);


    Message MessageRequestDtoPostToMessage(MessageRequestDto.Post post);

    Message MessageRequestDtoPatchToMessage(MessageRequestDto.Patch patch);
}
