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
        log.info("---------------------서비스11------------------------");
        log.info(request.getRoom().get(0).getName());
        log.info(request.getPackages().getName());
        log.info(request.getPackages().getName());
        log.info(request.getRoom().get(1).getName());
        log.info(request.getPackages().getName());
        log.info("---------------------서비스22------------------------");

        List<RoomPackage> roomPackageList = roomPackageService.createRoomPackage
                    (roomPackageMapper.PackageRequestDtoToPackage(request.getPackages()),
                            roomPackageMapper.RoomToRoomRequestDto(request.getRoom()));
        //응답
        log.info("---------------------확인1------------------------");
        List<RoomPackageResponseDto.Response> responseList = new ArrayList<>();

        List<Room> room1 = new ArrayList<>();
        Package pa = roomPackageList.get(0).getPackages();
        log.info(pa.getName());
        log.info("---------------------확인2------------------------");


        for(int i=0;i<roomPackageList.size();i++){
            Room rooms = roomPackageList.get(i).getRoom();
            room1.add(rooms);
        }
        log.info("---------------------확인3------------------------");

        RoomPackageResponseDto.Response response =
                new RoomPackageResponseDto.Response(roomPackageMapper.PackageToRequest(pa),roomPackageMapper.RoomToRequest(room1));

        log.info("---------------------확인4------------------------");
        log.info(response.getPackages().getName());
        log.info(response.getRoom().get(0).getName());
        log.info(response.getRoom().get(1).getName());
        log.info(response.getRoom().get(0).getName());
        log.info("---------------------55확인55------------------------");
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}


