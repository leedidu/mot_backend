package com.umc.mot.roomPackage.controller;

import com.umc.mot.packagee.entity.Package;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.mapper.RoomMapper;
import com.umc.mot.roomPackage.dto.RoomPackageRequestDto;
import com.umc.mot.roomPackage.dto.RoomPackageResponseDto;
import com.umc.mot.roomPackage.dto.RoomResponseDto;
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
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/roomPackage")
@Validated
@AllArgsConstructor
@Log4j2
public class RoomPackageController {

    private final RoomPackageService roomPackageService;
    private final RoomPackageMapper roomPackageMapper;
    private final RoomMapper roomMapper;

    @PostMapping
    public ResponseEntity RoomPackageCreateController(@Valid @RequestBody RoomPackageRequestDto.requestDto request){
        //요청 받은걸 RoomPackage에 넣어서 처리

        List<RoomPackage> roomPackageList = roomPackageService.createRoomPackage
                    (roomPackageMapper.PackageRequestDtoToPackage(request.getPackages()),
                            roomPackageMapper.RoomToRoomRequestDto(request.getRoom()));
        //응답
        List<RoomPackageResponseDto.Response> responseList = new ArrayList<>();

        List<Room> room1 = new ArrayList<>();
        Package pa = roomPackageList.get(0).getPackages();

        for(int i=0;i<roomPackageList.size();i++){
            Room rooms = roomPackageList.get(i).getRoom();
            room1.add(rooms);
        }

        RoomPackageResponseDto.Response response =
                new RoomPackageResponseDto.Response(roomPackageMapper.PackageToRequest(pa),roomPackageMapper.RoomToRequest(room1));

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }



    // Read
    @GetMapping
    public ResponseEntity getRoomPackage(@Positive @RequestParam int packageId){
     List<RoomResponseDto.Response> rooms =
             roomPackageMapper.RoomToRequest(roomPackageService.findRoomPackage(packageId));

        return new ResponseEntity<>(rooms,HttpStatus.OK);
    }



    @PatchMapping
    public ResponseEntity RoomPackagePatchController
            (@Valid @RequestBody RoomPackageRequestDto.requestDto request){
        List<RoomPackage> roomPackageList = roomPackageService.patchRoomPackage
                (roomPackageMapper.PackageRequestDtoToPackage(request.getPackages()),
                        roomPackageMapper.RoomToRoomRequestDto(request.getRoom()));
        //응답
        List<RoomPackageResponseDto.Response> responseList = new ArrayList<>();

        List<Room> room1 = new ArrayList<>();
        Package pa = roomPackageList.get(0).getPackages();

        for(int i=0;i<roomPackageList.size();i++){
            Room rooms = roomPackageList.get(i).getRoom();
            room1.add(rooms);
        }

        RoomPackageResponseDto.Response response =
                new RoomPackageResponseDto.Response(roomPackageMapper.PackageToRequest(pa),roomPackageMapper.RoomToRequest(room1));

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }



    // Delete
    @DeleteMapping("/{package-id}")
    public ResponseEntity deleteRoomPackage(@Positive @PathVariable("package-id") int packageId) {

        roomPackageService.deleteRoomPackage(packageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}




