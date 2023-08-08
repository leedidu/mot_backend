package com.umc.mot.roomPackage.dto;

import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class RoomPackageResponseDto {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Package pa;
        private List<Room> room;

    }
}
