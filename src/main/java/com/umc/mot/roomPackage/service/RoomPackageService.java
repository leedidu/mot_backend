package com.umc.mot.roomPackage.service;


import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.hotel.repository.HotelRepository;
import com.umc.mot.hotel.service.HotelService;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.packagee.mapper.PackageMapper;
import com.umc.mot.packagee.repository.PackageRepository;
import com.umc.mot.packagee.service.PackageService;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.mapper.RoomMapper;
import com.umc.mot.room.repository.RoomRepository;
import com.umc.mot.room.service.RoomService;
import com.umc.mot.roomPackage.entity.RoomPackage;
import com.umc.mot.roomPackage.repository.RoomPackageRepository;
import com.umc.mot.sellMember.entity.SellMember;
import com.umc.mot.token.entity.Token;
import com.umc.mot.token.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Log4j2
public class RoomPackageService {
    private final RoomPackageRepository roomPackageRepository;
    private final TokenService tokenService;
    private final RoomService roomService;
    private final HotelService hotelService;
    private final PackageService packageService;


    //패키지랑 룸저장하는거 룸패키지1 패키지1 작은방,
    // 룸패키지1 패키지1 큰방을 생각했는데
    // 아래와 같이 하면 룸패키지1 패키지1 작은방,
    // 룸패키지2 패키지1 큰방
    //패키지가 만들어지고 그 뒤에 패키지 안에 룸이 추가되어야 한다.
    public List<RoomPackage> createRoomPackage(Package pa, List<Room> rooms) {
        log.info("---------------------토큰확인 -------------------------");
        SellMember sellM = tokenService.getLoginSellMember();
        log.info(pa.getName());
        log.info(pa.getInfo());
        Package paTest = packageService.verifiedPackage(pa.getId());
        int id =paTest.getHotel().getId();

        log.info(pa.getName());
        log.info(pa.getInfo());
        List<RoomPackage> roomPackages = new ArrayList<>();

        log.info(rooms.size());
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            Package aPackage = pa;
            Room findroom = roomService.findRoomId(room.getId());
            log.info("---------------------룸확인  -------------------------");
            log.info(findroom.getName());
            log.info(findroom.getInfo());
            int id2 = findroom.getHotel().getId();
            log.info(id2);
            log.info("---------------------룸확인2  -------------------------");
            RoomPackage roomPackage = new RoomPackage();

            if (id==id2) {
                roomPackage.setPackages(pa);
                log.info("--------------------서비스1---------------------------");
                log.info(paTest.getInfo());
                log.info(paTest.getName());
                log.info(room.getCreatedAt());
                log.info(room.getModifiedAt());
                log.info("--------------------서비스2---------------------------");
                roomPackage.setRoom(room);
                roomPackages.add(roomPackage);
                roomPackageRepository.save(roomPackage);

            } else {

                throw new IllegalArgumentException("Cannot create room package for different hotels.");
            }

        }

        return roomPackages;

    }
}



