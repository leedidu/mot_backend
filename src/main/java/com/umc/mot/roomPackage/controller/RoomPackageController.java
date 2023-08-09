package com.umc.mot.roomPackage.controller;

import com.umc.mot.hotel.dto.HotelResponseDto;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.room.dto.RoomResponseDto;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.mapper.RoomMapper;
import com.umc.mot.roomPackage.dto.RoomPackageRequestDto;
import com.umc.mot.roomPackage.dto.RoomPackageResponseDto;
import com.umc.mot.roomPackage.entity.RoomPackage;
import com.umc.mot.roomPackage.mapper.RoomPackageMapper;
import com.umc.mot.roomPackage.service.RoomPackageService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/roomPackage")
@Validated
@AllArgsConstructor
@Log4j2
public class RoomPackageController {

    private final RoomPackageService roomPackageService;
    private final RoomPackageMapper roomPackageMapper;

    @PostMapping
    public ResponseEntity RoomPackageController(@Valid @RequestBody RoomPackageRequestDto.requestDto request){
        //요청 받은걸 RoomPackage에 넣어서 처리

        List<RoomPackage> roomPackageList = roomPackageService.createRoomPackage
                    (roomPackageMapper.RequestPackageToPackage(request.getPackages()),
                            roomPackageMapper.RequestRoomToRoom(request.getRoom()));
        //응답
        log.info("---------------------확인3------------------------");
        List<RoomPackageResponseDto.Response> responseList = new ArrayList<>();


        for (RoomPackage roomPackage : roomPackageList) {
            RoomPackageResponseDto.Response response = roomPackageMapper.RoomPackageToRoomPackageResponse(
                    roomPackage.getPackages(),
                    Collections.singletonList(roomPackage.getRoom())
            );
            responseList.add(response);
        }
        log.info("---------------------확인4------------------------");
        return new ResponseEntity<>(responseList, HttpStatus.CREATED);



    }
}


