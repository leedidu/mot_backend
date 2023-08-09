package com.umc.mot.room.controller;

import com.umc.mot.room.dto.RoomRequestDto;
import com.umc.mot.room.dto.RoomResponseDto;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.mapper.RoomMapper;
import com.umc.mot.room.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/room")
@Validated
@AllArgsConstructor
public class RoomController {

    private final com.umc.mot.room.service.RoomService roomService;
    private final RoomMapper roomMapper;

    // Create
    @PostMapping("/{hotel-id}")
    public ResponseEntity postRoom(@Valid @RequestBody RoomRequestDto.Post post
                                   ,@Positive @PathVariable("hotel-id") int hotelId) {
        Room room = roomService.createRoom(roomMapper.roomRequestDtoPostToRoom(post),hotelId);
        RoomResponseDto.Response response = roomMapper.roomToRoomResponseDto(room);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping
    public ResponseEntity getRoom(@Positive @RequestParam int roomId) {
        Room room = roomService.findRoomId(roomId);
        RoomResponseDto.Response response=roomMapper.roomToRoomResponseDto(room);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update
    @PatchMapping("/{room-id}")
    public ResponseEntity patchMember(@Positive @PathVariable("room-id") int roomId,
                                      @RequestBody RoomRequestDto.Patch patch) {
        patch.setId(roomId);
        Room room = roomService.patchRoom(roomMapper.roomRequestDtoPatchToRoom(patch));
        RoomResponseDto.Response response = roomMapper.roomToRoomResponseDto(room);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Delete
    @DeleteMapping("/{room-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("room-id") int roomId) {
        roomService.deleteRoom((roomId));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


