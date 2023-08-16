package com.umc.mot.reserve.service;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.hotel.service.HotelService;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.packagee.service.PackageService;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import com.umc.mot.purchaseMember.service.PurchaseMemberService;
import com.umc.mot.reserve.controller.ReserveController;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.reserve.repository.ReserveRepository;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.service.RoomService;
import com.umc.mot.roomPackage.service.RoomPackageService;
import com.umc.mot.token.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final TokenService tokenService;
    private final HotelService hotelService;
    private final RoomPackageService roomPackageService;
    private final RoomService roomService;
    private final PackageService packageService;

    public List<Hotel> findHotels(){ // 예약된 호텔 리스트 찾기
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        List<Hotel> hotellist = new ArrayList();
        for(Reserve reserve : purchaseMember.getReserves()) { // 예약 된거 확인
            hotellist.add(reserve.getHotel()); // 호텔 정보 추가
        }
        return hotellist;
    }

    public Map<Reserve, List<Room>> findRooms(){ //예약 정보 중 객실 정보
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Map<Reserve, List<Room>> reserveRooms = new HashMap<>();
        List<Room> rooms = new ArrayList<>();
        for(Reserve reserve : purchaseMember.getReserves()){
            for(int i = 0; i < reserve.getRoomsId().size(); i++){
                rooms.add(roomService.verifiedRoom(reserve.getRoomsId().get(i)));
            }
            reserveRooms.put(reserve, rooms);
//            reserveRooms.put(reserve, reserve.getRooms());
        }
        return reserveRooms;
    }

    public Map<Reserve, List<Package>> findPackages(){ //예약 정보 중 객실 정보
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Map<Reserve, List<Package>> reserveRooms = new HashMap<>();
        List<Package> packages = new ArrayList<>();
        for(Reserve reserve : purchaseMember.getReserves()){
            for(int i = 0; i < reserve.getPackagesId().size(); i++){
                packages.add(packageService.verifiedPackage(reserve.getPackagesId().get(i)));
            }
            reserveRooms.put(reserve, packages);
//            reservePackages.put(reserve, reserve.getPackages());
        }
        return reserveRooms;
    }

    // Read
    public List<Reserve> findReserve(){
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        List<Reserve> reserves = purchaseMember.getReserves();
        return reserves;
    }

/*
1. 예약 생성시 들어간 호텔 아이디를 이용해서 객실/패키지 찾기
2. 해당 객실의 예약식별아이디를 지금 예약아이디로 수정
* */

    //Create
    public Reserve createReserve(Reserve reserve, int hotelId, Integer packageId, Integer  roomId) {
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Hotel hotel = hotelService.verifiedHotel(hotelId);
        List<Room> rooms = roomPackageService.findRoomPackage(packageId);
        if(packageId != 0){
            reserve.getPackagesId().add(packageId);
            for(Room room : rooms){
                createReserve(reserve, hotelId, 0, room.getId()); // 패키지 예약할 경우 방까지 모두 예약된 상태로 변경
            }
        }
        if(roomId != 0){
            reserve.getRoomsId().add(roomId);
        }
        if(packageId != 0){
            reserve.getPackagesId().add(packageId);
        }
        if(roomId != 0){
            reserve.getRoomsId().add(roomId);
        }
        reserve.setPurchaseMember(purchaseMember);
        reserve.setHotel(hotel);
        return reserveRepository.save(reserve);
    }

    public List<Reserve> getReserve(Integer hotelId, Integer roomId, Integer packageId){
        Hotel hotel = hotelService.findHotel(hotelId); //호텔 정보를 가져옴
        List<Reserve> reservations = new ArrayList<>();
        if(!roomId.equals(null)){
            for(Reserve reserve : hotel.getReserves()){
                if(reserve.getRoomsId().contains(roomId)){
                    reservations.add(reserve);
                }
            }
        }
        if(!packageId.equals(null)){
            for(Reserve reserve : hotel.getReserves()){
                if(reserve.getPackagesId().contains(packageId)){
                    reservations.add(reserve);
                }
            }
        }
        return reservations;
    }

    public boolean checkReserve(ReserveRequestDto.Post post){
        List<Reserve> reserves = getReserve(post.getHotelId(), post.getRoomId(), post.getPackageId()); // 현재 예약하고자 하는 방의 모든 예약정보를 담고있음
        List<Boolean> check = new ArrayList<>();
        if(reserves.isEmpty()){
            return true;
        } else {
            if (!post.getRoomId().equals(null)) { // 객실을 예약할 경우
                for(int i = 0; i < reserves.size(); i++){
                    for(int j = 0; j < reserves.get(i).getRoomsId().size(); j++){
                        if(reserves.get(i).getRoomsId().get(j) == post.getRoomId()){
                            if(!checkDate(reserves.get(i).getCheckIn(), reserves.get(i).getCheckOut(), post.getCheckIn(), post.getCheckOut())){
                                check.add(false);
                            } else{
                                check.add(true);
                            }
                        }
                    }
                }
            }
            if(!post.getPackageId().equals(null)){ // 패키지를 예약할 경우
                List<Room> rooms = roomPackageService.findRoomPackage(post.getPackageId());
                for(int i = 0; i < reserves.size(); i++){
                    for(int j = 0; j < reserves.get(i).getPackagesId().size(); j++){
                        if(reserves.get(i).getPackagesId().get(j) == post.getPackageId()){
                            if(!checkDate(reserves.get(i).getCheckIn(), reserves.get(i).getCheckOut(), post.getCheckIn(), post.getCheckOut())){
                                check.add(false);
                            } else{
                                check.add(true);
                            }
                        }
                    }
                }
            }
        }
        if(check.contains(false)){
            return false;
        } else{
            return true;
        }
    }

    public boolean checkDate (LocalDate reserveCheckIn, LocalDate reserveCheckOut, LocalDate postCheckIn, LocalDate postCheckOut){ // 예약가능날짜 체크
        if(reserveCheckIn.isEqual(postCheckOut) || reserveCheckOut.isEqual(postCheckIn) || reserveCheckIn.isAfter(postCheckOut) || reserveCheckOut.isBefore(postCheckIn)){
            return true;
        } else{
            return false;
        }
    }

    // Read
    public Reserve findReserve(int reserveId){
        Reserve reserve = verifiedReserve(reserveId);
        return reserve;
    }



    // Update
    public Reserve patchReserve(Reserve reserve) {
        Reserve findReserve = verifiedReserve(reserve.getId());
        Optional.ofNullable(reserve.getCheckIn()).ifPresent(findReserve::setCheckIn);
        Optional.ofNullable(reserve.getCheckOut()).ifPresent(findReserve::setCheckOut);
        Optional.ofNullable(reserve.getPhone()).ifPresent(findReserve::setPhone);
        if(reserve.getPaymentPrice() != 0) findReserve.setPaymentPrice(reserve.getPaymentPrice());
        if(reserve.getPeopleNum() != 0) findReserve.setPeopleNum(reserve.getPeopleNum());

        return reserveRepository.save(findReserve);
    }

    // Delete
    public void deleteReserve(int reserveId) {
        Reserve reserve = verifiedReserve(reserveId);
        reserveRepository.delete(reserve);
    }

    // 객실 검증
    public Reserve verifiedReserve(int reserveId) {
        Optional<Reserve> member = reserveRepository.findById(reserveId);
        return member.orElseThrow(() -> new BusinessLogicException(ExceptionCode.RESERVE_NOT_FOUND));
    }
}