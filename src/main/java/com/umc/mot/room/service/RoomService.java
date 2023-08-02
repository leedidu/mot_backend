package com.umc.mot.room.service;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.repository.RoomRepository;
import com.umc.mot.sellMember.entity.SellMember;
import com.umc.mot.sellMember.repository.SellMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    //Create
    public Room createRoom(Room room) {

        return roomRepository.save(room);
    }

    // Read
    public Room findRoomId(int roomId){
        Room room = verifiedRoom(roomId);
        return room;
    }


    // Update
    public Room patchRoom(Room room) {
        Room findRoom = verifiedRoom(room.getId());
        Optional.ofNullable(room.getId()).ifPresent(findRoom::setId);
        Optional.ofNullable(room.getName()).ifPresent(findRoom::setName);
        Optional.ofNullable(room.getMinPeople()).ifPresent(findRoom::setMinPeople);
        Optional.ofNullable(room.getMaxPeople()).ifPresent(findRoom::setMaxPeople);
        Optional.ofNullable(room.getPrice()).ifPresent(findRoom::setPrice);



        return roomRepository.save(findRoom);
    }

    // Delete
    public void deleteRoom(int roomId) {
        Room room = verifiedRoom(roomId);
        roomRepository.delete(room);
    }

    // 객실 검증
    public Room verifiedRoom(int roomId) {
        Optional<Room> member = roomRepository.findById(roomId);
        return member.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND));

    }
}
