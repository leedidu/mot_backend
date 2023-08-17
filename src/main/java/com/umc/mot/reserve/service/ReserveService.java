package com.umc.mot.reserve.service;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.hotel.service.HotelService;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.packagee.service.PackageService;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import com.umc.mot.reserve.dto.ReserveRequestDto;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.reserve.repository.ReserveRepository;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.service.RoomService;
import com.umc.mot.roomPackage.service.RoomPackageService;
import com.umc.mot.sellMember.entity.SellMember;
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
    private final RoomPackageService roomPackageService;
    private final RoomService roomService;
    private final PackageService packageService;


    //Create
    public Reserve createReserve(Reserve reserve, int hotelId, Integer packageId, Integer  roomId) {
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Hotel hotel = hotelService.verifiedHotel(hotelId);
        List<Room> rooms = roomPackageService.findRoomPackage(packageId);
        if(packageId != 0){
            reserve.getPackagesId().add(packageId);
            for(Room room : rooms){ // 패키지 예약할 경우 방까지 모두 예약된 상태로 변경
                createReserve(reserve, hotelId, 0, room.getId());
            }
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
        return verifiedReserve(reserveId);
    }

    public List<Reserve> findReservelist(){
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        return purchaseMember.getReserves();
    }
    public Map<Reserve, Hotel> findHotels(){ // 예약된 호텔 리스트 찾기
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Map<Reserve, Hotel> reserveHotel = new HashMap<>();
        for(Reserve reserve : purchaseMember.getReserves()) { // 예약 된거 확인
            reserveHotel.put(reserve, reserve.getHotel());
        }
        return reserveHotel;
    }
    public Map<Reserve, Room> findRooms(){ //예약 정보 중 객실 정보
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Map<Reserve, Room> reserveRooms = new HashMap<>();
        for(Reserve reserve : purchaseMember.getReserves()){
            if(!reserve.getRoomsId().isEmpty()){
                Room room = roomService.verifiedRoom(reserve.getRoomsId().get(0));
                reserveRooms.put(reserve, room);
            }
        }
        return reserveRooms;
    }
    public Map<Reserve, Package> findPackages(){ //예약 정보 중 패키지 정보
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Map<Reserve, Package> reservePackages = new HashMap<>();
        for(Reserve reserve : purchaseMember.getReserves()){
            if(!reserve.getPackagesId().isEmpty()){
                Package packages = packageService.verifiedPackage(reserve.getPackagesId().get(0));
                reservePackages.put(reserve, packages);
            }
        }
        return reservePackages;
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

    //판매자 입장 - 기간 내 예약조회
    public List<Reserve> getPeriodreserve(int hotelId, LocalDate checkIn, LocalDate checkOut){
        tokenService.getLoginSellMember(); // 판매자 로그인 가져옴
        Hotel hotel = hotelService.verifiedHotel(hotelId); // 호텔을 가져옴
        // 호텔에 딸린 예약 내역을 가져 온 다음 -> 체크인 체크아웃 비교
        // 패키지또는객실이름, 기간, 패키지일 경우 룸타입, 인원
        List<Reserve> reserveList = new ArrayList<>();
        for(Reserve reserve : hotel.getReserves()){
            if(!(reserve.getCheckIn().isEqual(checkOut) || reserve.getCheckOut().isEqual(checkIn) || reserve.getCheckIn().isAfter(checkOut) || reserve.getCheckOut().isBefore(checkIn))){
                reserveList.add(reserve);
            }
        }
        return reserveList;
    }

    public Map<Reserve, Package> getPeriodPackage(List<Reserve> reserves){
        tokenService.getLoginSellMember();
        Map<Reserve, Package> packages = new HashMap<>();
        for(Reserve reserve : reserves) {
            if (!reserve.getPackagesId().isEmpty()) {
                Package packagee = packageService.verifiedPackage(reserve.getPackagesId().get(0));
                packages.put(reserve, packagee);
            }
        }
        return packages;
    }

    public Map<Reserve, Room> getPeriodRoom(List<Reserve> reserves){
        tokenService.getLoginSellMember();
        Map<Reserve, Room> rooms = new HashMap<>();
        for(Reserve reserve : reserves){
            if(!reserve.getRoomsId().isEmpty()){
                Room room = roomService.verifiedRoom(reserve.getRoomsId().get(0));
                rooms.put(reserve, room);
            }
        }
        return rooms;
    }

    // 판매자 입장 - 예약 상세보기
    // 이름, 기간, 번호, 객실
}