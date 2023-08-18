package com.umc.mot.reserve.controller;

import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.packagee.service.PackageService;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import com.umc.mot.reserve.mapper.ReserveMapper;
import com.umc.mot.reserve.service.ReserveService;
import com.umc.mot.reserve.dto.ReserveRequestDto;
import com.umc.mot.reserve.dto.ReserveResponseDto;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.room.entity.Room;
import com.umc.mot.room.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reserve")
@Validated
@AllArgsConstructor
public class ReserveController {
    private final ReserveService reserveService;
    private final ReserveMapper reserveMapper;
    private final PackageService packageService;
    private final RoomService roomService;

    // Create
    @PostMapping
    public ResponseEntity postReserve(@Valid @RequestBody ReserveRequestDto.Post post) {
        boolean checkReserve = reserveService.checkReserve(post);

        if(checkReserve){
            Reserve reserve = reserveService.createReserve(reserveMapper.ReserveRequestDtoPostToReserve(post), post.getHotelId(), post.getPackageId(), post.getRoomId());
            ReserveResponseDto.Response response = reserveMapper.ReserveToReserveResponseDto(reserve);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else{
            throw new IllegalArgumentException("You can't make a reservation for that date because it's already booked.");
        }
    }

    // Read
    @GetMapping
    public ResponseEntity getReserve() {
        List<Reserve> reserve = reserveService.findReservelist();
        Map<Reserve, Room> findRooms = reserveService.findRooms();
        Map<Reserve, Package> findPackages = reserveService.findPackages();
        Map<Reserve, Hotel> findHotel = reserveService.findHotels();
        List<ReserveResponseDto.Get> gets = new ArrayList<>();
        for(Reserve reserve1 : reserve){
            Hotel hotel = findHotel.get(reserve1);
            Room room = findRooms.get(reserve1);
            Package packagee = findPackages.get(reserve1);
            ReserveResponseDto.HotelInfo hotelResponse = reserveMapper.ResponseToHotel(hotel); // 호텔 매핑
            if(packagee != null){
                ReserveResponseDto.PackageInfo packageResponse = reserveMapper.ResponseToPackage(packagee);
                ReserveResponseDto.Get getResponse = reserveMapper.ReserveToGetResponseDto(reserve1, hotelResponse, null, packageResponse);
                gets.add(getResponse);
            } else if (room != null) {
                ReserveResponseDto.RoomInfo roomResponse = reserveMapper.ResponseToRoom(room);
                ReserveResponseDto.Get getResponse = reserveMapper.ReserveToGetResponseDto(reserve1, hotelResponse, roomResponse, null);
                gets.add(getResponse);
            }
        }
        return new ResponseEntity<>(gets, HttpStatus.OK);
    }

    // Update
    @PatchMapping("/{reserve-id}")
    public ResponseEntity patchMember(@Positive @PathVariable("reserve-id") int reserveId,
                                      @RequestBody ReserveRequestDto.Patch patch) {
        patch.setId(reserveId);
        Reserve reserve = reserveService.patchReserve(reserveMapper.ReserveRequestDtoPatchToReserve(patch));
        ReserveResponseDto.Response response = reserveMapper.ReserveToReserveResponseDto(reserve);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Delete
    @DeleteMapping("/{reserve-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("reserve-id") int reserveId) {
        reserveService.deleteReserve((reserveId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //판매자 입장
    @GetMapping("/check/{hotel-id}")
    public ResponseEntity getPeriodreserve(@Positive @PathVariable("hotel-id") int hotelId,
                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkIn,
                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOut){
        List<Reserve> reserves = reserveService.getPeriodreserve(hotelId, checkIn, checkOut);
        Map<Reserve, Package> packages = reserveService.getPeriodPackage(reserves);
        Map<Reserve, Room> rooms = reserveService.getPeriodRoom(reserves);
        List<ReserveResponseDto.ReserveInfo> reserveInfoList = new ArrayList<>();
        for(Reserve reserve : reserves){
            Package packagee = packages.get(reserve);
            Room room = rooms.get(reserve);
            if(packagee != null){ // 패키지를 예약한 경우
                ReserveResponseDto.PackageInfo packageResponse = reserveMapper.ResponseToPackage(packagee);
                ReserveResponseDto.ReserveInfo getResponse = reserveMapper.ResponseToReserve(reserve, packageResponse, null);
                reserveInfoList.add(getResponse);
            } else if(room != null){ // 객실을 에약한 경우
                ReserveResponseDto.RoomInfo roomInfo = reserveMapper.ResponseToRoom(room);
                ReserveResponseDto.ReserveInfo getResponse = reserveMapper.ResponseToReserve(reserve, null, roomInfo);
                reserveInfoList.add(getResponse);
            }
        }
        return new ResponseEntity<>(reserveInfoList, HttpStatus.OK);
    }

    @GetMapping("/detail/{reserve-id}")
    public ResponseEntity getDetailInfo(@Positive @PathVariable("reserve-id") int reserveId){
        Map<Reserve, PurchaseMember> detail = reserveService.getDetailInfo(reserveId);
        Reserve reserve = reserveService.verifiedReserve(reserveId);
        if(!reserve.getPackagesId().isEmpty()){
            Package packagee = packageService.verifiedPackage(reserve.getPackagesId().get(0));
            ReserveResponseDto.PackageInfo packageResponse = reserveMapper.ResponseToPackage(packagee);
            ReserveResponseDto.DetailInfo detailInfo = reserveMapper.ResponseToDetail(reserve, detail.get(reserve), packageResponse, null);
            return new ResponseEntity<>(detailInfo, HttpStatus.OK);
        } else if(!reserve.getRoomsId().isEmpty()){
            Room room = roomService.verifiedRoom(reserve.getRoomsId().get(0));
            ReserveResponseDto.RoomInfo roomInfo = reserveMapper.ResponseToRoom(room);
            ReserveResponseDto.DetailInfo detailInfo = reserveMapper.ResponseToDetail(reserve, detail.get(reserve), null, roomInfo);
            return new ResponseEntity<>(detailInfo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
