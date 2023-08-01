package com.umc.mot.room.mapper;

import com.umc.mot.room.dto.RoomRequestDto;
import com.umc.mot.room.dto.RoomResponseDto;
import com.umc.mot.room.entity.Room;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomResponseDto.Response roomToRoomResponseDto(Room room);
    Room roomRequestDtoPostToRoom(RoomRequestDto.Post post);
    Room roomRequestDtoPatchToRoom(RoomRequestDto.Patch patch);
}

