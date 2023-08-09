package com.umc.mot.roomPackage.mapper;


import com.umc.mot.hotel.dto.HotelRequestDto;

import com.umc.mot.packagee.entity.Package;
import com.umc.mot.room.entity.Room;
import com.umc.mot.roomPackage.dto.*;
import com.umc.mot.roomPackage.entity.RoomPackage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomPackageMapper {
    RoomPackageResponseDto.Response RoomPackageToRoomPackageResponse(PackageResponseDto.Response pa
                                                ,List<RoomResponseDto.Response> room);

    Package PackageRequestDtoToPackage(PackageRequestDto.Request request);
    List<Room> RoomToRoomRequestDto(List<RoomRequestDto.Request> roomRequestDto);

    PackageResponseDto.Response PackageToRequest(Package pa);

    List<RoomResponseDto.Response> RoomToRequest(List<Room> room);


}
