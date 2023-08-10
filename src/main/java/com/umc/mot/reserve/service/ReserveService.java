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
import com.umc.mot.token.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final TokenService tokenService;
    private final HotelService hotelService;

    public List<Hotel> findHotels(){ // 예약된 호텔 리스트 찾기
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        List<Hotel> hotellist = new ArrayList();
        for(Reserve reserve : purchaseMember.getReserves()) { // 예약 된거 확인
            if(reserve.getPackages() != null && !reserve.getPackages().isEmpty()){
                for(Package packagee : reserve.getPackages()){
                    hotellist.add(packagee.getHotel());
                }
            } if(reserve.getRooms() != null && !reserve.getRooms().isEmpty()){
                for(Room room : reserve.getRooms()){
                    hotellist.add(room.getHotel());
                }
            }
        }
        return hotellist;
    }

    public Map<Reserve, List<Room>> findRooms(){ //예약 정보 중 객실 정보
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Map<Reserve, List<Room>> reserveRooms = new HashMap<>();
        for(Reserve reserve : purchaseMember.getReserves()){
            reserveRooms.put(reserve, reserve.getRooms());
        }
        return reserveRooms;
    }

    public Map<Reserve, List<Package>> findPackages(){ //예약 정보 중 패키지 정보
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Map<Reserve, List<Package>> reservePackages = new HashMap<>();
        for(Reserve reserve : purchaseMember.getReserves()){
            reservePackages.put(reserve, reserve.getPackages());
        }
        return reservePackages;
    }

    //        if((reserve.getCheckIn().isBefore(post.getCheckIn()) && reserve.getCheckOut().isBefore(post.getCheckOut()))
//                || (reserve.getCheckIn().isBefore(post.getCheckIn()) && reserve.getCheckOut().isAfter(post.getCheckOut()))
//                || (reserve.getCheckIn().isAfter(post.getCheckIn()) && reserve.getCheckOut().isBefore(post.getCheckOut()))
//                || (reserve.getCheckIn().isAfter(post.getCheckIn()) && reserve.getCheckOut().isAfter(post.getCheckOut()))){
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);}
//        else{
//            return new ResponseEntity<>(response, HttpStatus.CREATED);
//        }
    //Create
    public Reserve createReserve(Reserve reserve, int hotelId) {
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Hotel hotel = hotelService.verifiedHotel(hotelId);
        reserve.setPurchaseMember(purchaseMember);
        reserve.setHotel(hotel);
        return reserveRepository.save(reserve);
    }


    // Read
    public Reserve findReserveId(int roomId){
        Reserve reserve = verifiedReserve(roomId);
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