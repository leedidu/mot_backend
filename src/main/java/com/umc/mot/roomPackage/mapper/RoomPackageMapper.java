package com.umc.mot.roomPackage.mapper;


import com.umc.mot.hotel.dto.HotelRequestDto;

import com.umc.mot.packagee.entity.Package;
import com.umc.mot.room.entity.Room;
import com.umc.mot.roomPackage.dto.RoomPackageRequestDto;
import com.umc.mot.roomPackage.dto.RoomPackageResponseDto;
import com.umc.mot.roomPackage.entity.RoomPackage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomPackageMapper {
    RoomPackageResponseDto.Response RoomPackageToRoomPackageResponse(Package pa,List<Room> room);

    RoomPackage RoomPackageRequsetToRoomPackage(RoomPackageRequestDto.requestDto requestDto);

    List<Room> RequestRoomToRoom(List<Room> room);
    Package RequestPackageToPackage(Package paa);


}
