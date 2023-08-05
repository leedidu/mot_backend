package com.umc.mot.roomPackage.service;


import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.hotel.repository.HotelRepository;
import com.umc.mot.hotel.service.HotelService;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.packagee.repository.PackageRepository;
import com.umc.mot.packagee.service.PackageService;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.repository.RoomRepository;
import com.umc.mot.room.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class RoomPackageService {
    private final RoomRepository roomRepository;
    private final PackageRepository packageRepository;
    private final RoomService roomService;
    private final PackageService packageService;

    public Room roomPackage(int roomId, int packageId) {
        Room room = roomService.findRoomId(roomId);
        Package pa = packageService.findPackage(packageId);
        if(room.getHotel().equals(pa.getHotel())) {
        }
        return roomRepository.save(room);
    }


}
