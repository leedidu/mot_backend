package com.umc.mot.room.mapper;

import com.umc.mot.room.dto.RoomRequestDto;
import com.umc.mot.room.dto.RoomResponseDto;
import com.umc.mot.room.entity.Room;
import com.umc.mot.sellMember.dto.SellMemberRequestDto;
import com.umc.mot.sellMember.entity.SellMember;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomResponseDto.Response RoomToRoomResponseDto(Room room);
    Room RoomRequestDtoPostToRoom(RoomRequestDto.Post post);
    Room RoomRequestDtoPatchToRoom(RoomRequestDto.Patch patch);
}

