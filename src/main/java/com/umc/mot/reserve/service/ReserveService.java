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
    public Reserve createReserve(Reserve reserve, int hotelId, List<Integer> packageId, List<Integer> roomId) {
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Hotel hotel = hotelService.verifiedHotel(hotelId);
        if(packageId != null){
            for(int i = 0; i < packageId.size(); i++){
                Package packagee = packageService.verifiedPackage(packageId.get(i));
                if(packagee.getMinPeople() <= reserve.getPeopleNum() && reserve.getPeopleNum() <= packagee.getMaxPeople()){ // 인원체크
                    reserve.getPackagesId().add(packageId.get(i));
                    List<Room> rooms = roomPackageService.findRoomPackage(packageId.get(i));
                    List<Integer> roomsId = new ArrayList<>();
                    for(Room room : rooms){ // 패키지 예약할 경우 방까지 모두 예약된 상태로 변경
                        roomsId.add(room.getId());
                    }
                    createReserve(reserve, hotelId, null, roomsId);
                } else{
                    throw new IllegalArgumentException("Check your peoplenum");
                }
            }
        } if(roomId != null){
            for(int i = 0; i < roomId.size(); i++){
                Room room = roomService.verifiedRoom(roomId.get(i));
                if(room.getMinPeople() <= reserve.getPeopleNum() && reserve.getPeopleNum() <= room.getMaxPeople()){
                    reserve.getRoomsId().add(roomId.get(i));
                } else{
                    throw new IllegalArgumentException("Check your peoplenum");
                }
            }
        }
        reserve.setPurchaseMember(purchaseMember);
        reserve.setHotel(hotel);
        return reserveRepository.save(reserve);
    }

    public List<Reserve> getReserve(Integer hotelId, List<Integer> roomId, List<Integer> packageId){
        Hotel hotel = hotelService.findHotel(hotelId); //호텔 정보를 가져옴
        List<Reserve> reservations = new ArrayList<>();
        if(packageId != null){
            for(Reserve reserve : hotel.getReserves()){
                if(!Collections.disjoint(reserve.getPackagesId(), packageId)){
                    reservations.add(reserve);
                }
            }
        }
        if(roomId != null){
            for(Reserve reserve : hotel.getReserves()){
                if(!Collections.disjoint(reserve.getRoomsId(), roomId)){
                    reservations.add(reserve);
                }
            }
        }
        return reservations;
    }

    public boolean checkReserve(ReserveRequestDto.Post post){
        List<Reserve> reserves = getReserve(post.getHotelId(), post.getRoomId(), post.getPackageId()); // 현재 예약하고자 하는 방의 모든 예약정보를 담고있음
        List<Boolean> check = new ArrayList<>(); // 확인용
        List<Room> reservedRoom = new ArrayList<>(); // 예약된 객실
        List<Room> packageRoom = new ArrayList<>(); // 에약하고자 하는 객실
        if(reserves.isEmpty()){
            return true;
        } else { // 현재 에약하고자 하는 객실 - 예약이 겹치는 객실들 비교
            for(Reserve reserve : reserves){
                if(!checkDate(reserve.getCheckIn(), reserve.getCheckOut(), post.getCheckIn(), post.getCheckOut())){ // 예약날짜가 겹치는 경우
                    if(reserve.getRoomsId() != null){
                        for(int i = 0; i < reserve.getRoomsId().size(); i++){
                            reservedRoom.add(roomService.verifiedRoom(reserve.getRoomsId().get(i))); // 객실 예약일 경우 -> 각 객실을 모두 추가
                        }
                    }
                    if(reserve.getPackagesId() != null){
                        // 패키지를 예약할 때 .. 패키지의 아
                        roomPackageService.findRoomPackage(reserve.getPackagesId().get(0));
                        for(int i = 0; i < reserve.getPackagesId().size(); i++){
                            reservedRoom.addAll(roomPackageService.findRoomPackage(reserve.getPackagesId().get(i))); // 패키지 예약일 경우 -> 해당 객실들을 모두 추가
                        }
                    }
                }
            }
            if(post.getPackageId() != null){ // 패키지 예약을 하고자 하는 경우
                for(int i = 0; i < post.getPackageId().size(); i++){ // 예약하고자 하는 패키지에 딸린 객실들을 모두 추가
                    packageRoom.addAll(roomPackageService.findRoomPackage(post.getPackageId().get(i))); //예약하고자 하는 패키지의 객실들
                }
            }
            if(post.getRoomId() != null){ // 객실 예약을 하고자 하는 경우
                for(int i = 0; i < post.getRoomId().size(); i++){ // 예약하고자 하는 객실들을 모두 추가
                    packageRoom.add(roomService.verifiedRoom(post.getRoomId().get(i)));
                }
            } // 예약 객실 전처리 완료
        }
        System.out.println("패키지 번호 : " + packageRoom.size());
        System.out.println("예약된 객실 번호 : " + reservedRoom.size());
        check.add(Collections.disjoint(packageRoom, reservedRoom)); // 예약하고자 하는 객실 - 예약된 객실들 겹치는게 있을 경우 false 추가

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

    public Map<Reserve, List<Room>> findRooms(){ //예약 정보 중 객실 정보
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Map<Reserve, List<Room>> reserveRooms = new HashMap<>();
        for(Reserve reserve : purchaseMember.getReserves()){
            List<Room> roomList = new ArrayList<>();
            if(!reserve.getRoomsId().isEmpty()){
                for(int i = 0; i < reserve.getRoomsId().size(); i++){
                    Room room = roomService.verifiedRoom(reserve.getRoomsId().get(i));
                    roomList.add(room);
                }
                reserveRooms.put(reserve, roomList);
            }
        }
        return reserveRooms;
    }
    public Map<Reserve, List<Package>> findPackages(){ //예약 정보 중 패키지 정보
        PurchaseMember purchaseMember = tokenService.getLoginPurchaseMember();
        Map<Reserve, List<Package>> reservePackages = new HashMap<>();
        for(Reserve reserve : purchaseMember.getReserves()){
            List<Package> packageList = new ArrayList<>();
            if(!reserve.getPackagesId().isEmpty()){
                for(int i = 0; i < reserve.getPackagesId().size(); i++){
                    Package packages = packageService.verifiedPackage(reserve.getPackagesId().get(i));
                    packageList.add(packages);
                }
            }
            reservePackages.put(reserve, packageList);
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

    public Map<Reserve, List<Package>> getPeriodPackage(List<Reserve> reserves){
        tokenService.getLoginSellMember();
        Map<Reserve, List<Package>> packages = new HashMap<>();
        for(Reserve reserve : reserves) {
            if (!reserve.getPackagesId().isEmpty()) {
                List<Package> packageList = new ArrayList<>();
                for(int i = 0; i < reserve.getPackagesId().size(); i++){
                    Package packagee = packageService.verifiedPackage(reserve.getPackagesId().get(i));
                    packageList.add(packagee);
                }
                packages.put(reserve, packageList);
            }
        }
        return packages;
    }

    public Map<Reserve, List<Room>> getPeriodRoom(List<Reserve> reserves){
        tokenService.getLoginSellMember();
        Map<Reserve, List<Room>> rooms = new HashMap<>();
        for(Reserve reserve : reserves){
            if(!reserve.getRoomsId().isEmpty()){
                List<Room> roomList = new ArrayList<>();
                for(int i = 0; i < reserve.getRoomsId().size(); i++){
                    Room room = roomService.verifiedRoom(reserve.getRoomsId().get(i));
                    roomList.add(room);
                }
                rooms.put(reserve, roomList);
            }
        }
        return rooms;
    }

    // 판매자 입장 - 예약 상세보기
    // 이름, 기간, 번호, 객실
    public Map<Reserve, PurchaseMember> getDetailInfo(int reserveId){
        tokenService.getLoginSellMember();
        Map<Reserve, PurchaseMember> detailInfo = new HashMap<>();
        Reserve reserve = verifiedReserve(reserveId);
        detailInfo.put(reserve, reserve.getPurchaseMember());
        return detailInfo;
    }
}