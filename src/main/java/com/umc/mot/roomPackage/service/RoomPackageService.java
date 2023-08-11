package com.umc.mot.roomPackage.service;


import com.umc.mot.packagee.entity.Package;
import com.umc.mot.packagee.repository.PackageRepository;
import com.umc.mot.packagee.service.PackageService;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.repository.RoomRepository;
import com.umc.mot.room.service.RoomService;
import com.umc.mot.roomPackage.entity.RoomPackage;
import com.umc.mot.roomPackage.repository.RoomPackageRepository;
import com.umc.mot.sellMember.entity.SellMember;
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
    private final PackageService packageService;
    private final RoomRepository roomRepository;


    //패키지랑 룸저장하는거 룸패키지1 패키지1 작은방,
    // 룸패키지1 패키지1 큰방을 생각했는데
    // 아래와 같이 하면 룸패키지1 패키지1 작은방,
    // 룸패키지2 패키지1 큰방
    //패키지가 만들어지고 그 뒤에 패키지 안에 룸이 추가되어야 한다.
    public List<RoomPackage> createRoomPackage(Package pa, List<Room> rooms) {
        SellMember sellM = tokenService.getLoginSellMember();
        Package paTest = packageService.verifiedPackage(pa.getId());
        int id =paTest.getHotel().getId();

        List<RoomPackage> roomPackages = new ArrayList<>();

        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            Package aPackage = pa;
            Room findroom = roomService.findRoomId(room.getId());
            int id2 = findroom.getHotel().getId();
            RoomPackage roomPackage = new RoomPackage();

            if (id==id2) {
                roomPackage.setPackages(pa);
                roomPackage.setRoom(room);
                roomPackages.add(roomPackage);
                RoomPackage roomPackage1 = roomPackageRepository.save(roomPackage);
                findroom.getRoomPackages().add(roomPackage1);
                paTest.getRoomPackages().add(roomPackage1);


            } else {

                throw new IllegalArgumentException("Cannot create room package for different hotels.");
            }

        }

        return roomPackages;

    }

    //read
    public List<Room> findRoomPackage(int packageId){
        List<Integer> roomPackage = roomPackageRepository.findRoomByPackage(packageId);
        if(roomPackage.size() == 0)
            log.info("불러온 값이 없습니다.!!!!!!!!!!!!!!!!!!!!!");
        else {
            log.info(roomPackage.get(0));
            log.info(roomPackage.get(1));
            log.info("값을 무사히 불러왔습니다.!!!!!!!!!!!!!!!!!!!");

        }

        List<Room> rooms= new ArrayList<>();

        for (int i=0;i<roomPackage.size();i++){
            Room room = roomService.findRoomId(roomPackage.get(i));
            rooms.add(room);


        }
        log.info("리스트에 값이 넣어졌습니다!!!!!!!!!!!!!!!!!");
        log.info(rooms.get(0).getName());
        log.info(rooms.get(0).getCreatedAt());
        log.info(rooms.get(1).getName());
        log.info(rooms.get(1).getCreatedAt());
        log.info("리스트에 값이 넣어졌습니다!!!!!!!!!!!!!!!!!");


        return rooms;

    }


/*

    //Update
    public Heart patchRoomPackage(Heart heart){
        Heart findHeart = verifiedHeart(heart.getId());
        Optional.ofNullable(heart.getId()).ifPresent(findHeart::setId);
        return heartRepository.save(findHeart);
    }



    //Delete
    public void deleteHeart(int heartId){
        Heart heart = verifiedHeart(heartId);
        heartRepository.delete(heart);
    }

    public RoomPackage verifiedRP(int RPId) {


        List<RoomPackage> roomPackageList =new ArrayList<>();
        for
        Optional<RoomPackage> roomPackage = roomPackageRepository.findAll(RPId);
        return roomPackage.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ROOM_PACKAGE_NOT_FOUND));

    }





 */
}



