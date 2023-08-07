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

/*
    public Room roomPackage(int roomPackageId, int roomId, List<Integer> packageIds) {
        Room room = roomService.findRoomId(roomId);

        // 여러 개의 packageId를 순회하며 각각의 패키지를 추가
        for (Integer packageId : packageIds) {
            Package pa = packageService.findPackage(packageId);
            if (room.getHotel().equals(pa.getHotel())) {
                // 여기서 패키지를 room에 추가하는 로직을 작성
            } else {
                // 다른 호텔의 패키지는 처리할 수 없는 경우에 대한 로직
            }
        }

        return roomRepository.save(room);
    }
}
 */
}
