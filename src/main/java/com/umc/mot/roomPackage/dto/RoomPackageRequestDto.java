package com.umc.mot.roomPackage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class RoomPackageRequestDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class requestDto {
        private PackageRequestDto.Request packages;
        private List<RoomRequestDto.Request> room;

    }




}
