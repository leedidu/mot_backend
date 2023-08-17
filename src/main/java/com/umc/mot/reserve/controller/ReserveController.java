package com.umc.mot.reserve.controller;

import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.reserve.mapper.ReserveMapper;
import com.umc.mot.reserve.service.ReserveService;
import com.umc.mot.reserve.dto.ReserveRequestDto;
import com.umc.mot.reserve.dto.ReserveResponseDto;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.room.entity.Room;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
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
            ReserveResponseDto.Hotel hotelResponse = reserveMapper.ResponseToHotel(hotel); // 호텔 매핑

            if(packagee != null){
                ReserveResponseDto.Package packageResponse = reserveMapper.ResponseToPackage(packagee);
                ReserveResponseDto.Get getResponse = (reserveMapper.ReserveToGetResponseDto(reserve1, hotelResponse, null, packageResponse));
                gets.add(getResponse);
            } else if (room != null) {
                ReserveResponseDto.Room roomResponse = reserveMapper.ResponseToRoom(room);
                ReserveResponseDto.Get getResponse = (reserveMapper.ReserveToGetResponseDto(reserve1, hotelResponse, roomResponse, null));
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
}
