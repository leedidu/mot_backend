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
import com.umc.mot.reserve.dto.ReserveRequestDto;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.reserve.repository.ReserveRepository;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.service.RoomService;
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
    private final RoomService roomService;

    public List<Hotel> findHotels(){ // 예약된 호텔 리스트 찾기
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        List<Hotel> hotellist = new ArrayList();
        for(Reserve reserve : purchaseMember.getReserves()) { // 예약 된거 확인
        }
        return hotellist;
    }

    public Map<Reserve, List<Room>> findRooms(){ //예약 정보 중 객실 정보
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Map<Reserve, List<Room>> reserveRooms = new HashMap<>();
        for(Reserve reserve : purchaseMember.getReserves()){
//            reserveRooms.put(reserve, reserve.getRooms());
        }
        return reserveRooms;
    }

    public Map<Reserve, List<Package>> findPackages(){ //예약 정보 중 패키지 정보
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Map<Reserve, List<Package>> reservePackages = new HashMap<>();
        for(Reserve reserve : purchaseMember.getReserves()){
//            reservePackages.put(reserve, reserve.getPackages());
        }
        return reservePackages;
    }

/*
1. 예약 생성시 들어간 호텔 아이디를 이용해서 객실/패키지 찾기
2. 해당 객실의 예약식별아이디를 지금 예약아이디로 수정
* */

    //Create
    public Reserve createReserve(Reserve reserve, int hotelId, Integer packageId, Integer  roomId) {
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Hotel hotel = hotelService.verifiedHotel(hotelId);
        if(packageId != 0){
            for(Package packages : hotel.getPackages()){
                if(packages.getHotel() == hotel){
//                    packages.setReserve(reserve);
                }
            }
        }
//        if(roomId != 0){
//            for(Room room : hotel.getRooms()){
//                if(room.getHotel() == hotel){
//                    room.setReserve(reserve);
//                }
//            }
//        }
        reserve.setPurchaseMember(purchaseMember);
        reserve.setHotel(hotel);
        return reserveRepository.save(reserve);
    }


/*
1. 객실 패키지 테이블에서 호텔 아이디가 같은걸 찾음
2. 객실 테이블에서 그 예약 식별자를 가져옴
3. 체크인 아웃을 비교
* */
    public boolean checkReserve(ReserveRequestDto.Post post){
        Hotel hotel = hotelService.findHotel(post.getHotelId()); //호텔 정보를 가져옴
        if(post.getRoomId() != 0){ // 객실을 예약할때
            Room room = roomService.findRoomId(post.getRoomId());
//            if(room.getReserve() == null) {return true;}
//            else{
//                if(room.getReserve().getCheckIn().isEqual(post.getCheckIn())) {return false;}
//                else{
//                    if(room.getReserve().getCheckIn().isEqual(post.getCheckOut())
//                            || room.getReserve().getCheckOut().isEqual(post.getCheckIn())
//                            || room.getReserve().getCheckIn().isAfter(post.getCheckOut())
//                            || room.getReserve().getCheckOut().isBefore(post.getCheckIn())){
//                        return true;
//                    } else{
//                        return false;
//                    }
//                }
//            }
        }
        return false;
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