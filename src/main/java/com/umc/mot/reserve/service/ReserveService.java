package com.umc.mot.reserve.service;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.packagee.service.PackageService;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import com.umc.mot.purchaseMember.service.PurchaseMemberService;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.reserve.repository.ReserveRepository;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.service.RoomService;
import com.umc.mot.token.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final PurchaseMemberService purchaseMemberService;
    private final RoomService roomService;
    private final PackageService packageService;
    private final TokenService tokenService;

    /*
    * 첫번째 for : 구매자의 예약확인
    * if문 안에 for : 각 방의 호텔정보 얻기
    * 해서 각방의 호텔정보를 List<Hotel>안에 넣어서 반환
    * */

    public List<Hotel> findHotels(){ // 예약된 호텔 리스트 찾기
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        List<Hotel> hotellist = new ArrayList();
        for(Reserve reserve : purchaseMember.getReserves()) { // 예약 된거 확인
            if(reserve.getPackages() != null && !reserve.getPackages().equals("")){
                for(Package packagee : reserve.getPackages()){
                    hotellist.add(packagee.getHotel());
                }
            } if(reserve.getRooms() != null && !reserve.getRooms().equals("")){
                for(Room room : reserve.getRooms()){
                    hotellist.add(room.getHotel());
                }
            }
        }
        return hotellist;
    }

    public Map<Reserve, List<Room>> findRooms(){

        return null;
    }



    //Create
    public Reserve createReserve(Reserve reserve) {
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
        Optional.ofNullable(reserve.getId()).ifPresent(findReserve::setId);
        Optional.ofNullable(reserve.getPaymentPrice()).ifPresent(findReserve::setPaymentPrice);
        Optional.ofNullable(reserve.getCheckIn()).ifPresent(findReserve::setCheckIn);
        Optional.ofNullable(reserve.getCheckOut()).ifPresent(findReserve::setCheckOut);
        Optional.ofNullable(reserve.getPhone()).ifPresent(findReserve::setPhone);
        Optional.ofNullable(reserve.getPeopleNum()).ifPresent(findReserve::setPeopleNum);

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
