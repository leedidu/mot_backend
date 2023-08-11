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
    public ResponseEntity RoomPackageController(@Valid @RequestBody RoomPackageRequestDto.requestDto request){
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
     log.info("-----------------룸패키지 컨트롤러-------------------------");
        log.info(rooms.get(0).getName());
        log.info(rooms.get(0).getCreatedAt());
        log.info(rooms.get(1).getName());
        log.info(rooms.get(1).getCreatedAt());
        log.info("-----------------룸패키지 컨트롤러-------------------------");

        return new ResponseEntity<>(rooms,HttpStatus.OK);
    }


/*
    // Update
    @PatchMapping("/{heart-id}")
    public ResponseEntity patchRoomPackage(@Positive @PathVariable("heart-id") int heartId,
                                     @RequestBody HeartRequestDto.Patch patch) {
        patch.setId(heartId);
        Heart heart = heartService.patchHeart(heartMapper.HeartRequestDtoPatchToHeart(patch));
        HeartResponseDto.Response response =heartMapper.HeartToHeartResponseDto(heart);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Delete
    @DeleteMapping("/{package-id}")
    public ResponseEntity deleteRoomPackage(@Positive @PathVariable("package-id") int heartId) {


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


 */
}




