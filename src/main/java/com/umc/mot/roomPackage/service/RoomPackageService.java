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

        List<Room> rooms= new ArrayList<>();

        for (int i=0;i<roomPackage.size();i++){
            Room room = roomService.findRoomId(roomPackage.get(i));
            rooms.add(room);


        }


        return rooms;

    }




    //Update
    public List<RoomPackage> patchRoomPackage(Package pa,List<Room> rooms){
        roomPackageRepository.deleteRoomPackageByPackage(pa.getId());
        SellMember sell = tokenService.getLoginSellMember();

        Package paTest = packageService.verifiedPackage(pa.getId());
        int id =paTest.getHotel().getId();

        List<RoomPackage> roomPackages = new ArrayList<>();

        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            Package aPackage = pa;
            Room findroom = roomService.findRoomId(room.getId());
            int id2 = findroom.getHotel().getId();
            RoomPackage roomPackage = new RoomPackage();

            if (id == id2) {
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



    //Delete
    public void deleteRoomPackage(int packageId){
        SellMember sell = tokenService.getLoginSellMember();
        roomPackageRepository.deleteRoomPackageByPackage(packageId);
    }

}



