package com.umc.mot.roomPackage.controller;

import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.room.dto.RoomResponseDto;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.mapper.RoomMapper;
import com.umc.mot.roomPackage.dto.RoomPackageRequestDto;
import com.umc.mot.roomPackage.entity.RoomPackage;
import com.umc.mot.roomPackage.service.RoomPackageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/roomPackage")
@Validated
@AllArgsConstructor
public class RoomPackageController {

    private final RoomPackageService roomPackageService;
    private final RoomMapper roomMapper;

    @PostMapping
    public ResponseEntity RoomPackageController(@Valid @RequestBody RoomPackageRequestDto.requestDto request){
        Room room = roomMapper.roomRequestDtoPostToRoom(request.getRoom());
        Package pa = request.getPa();
        RoomPackage roomPackage = roomPackageService.createRoomPackage(pa,room);
    }
}
