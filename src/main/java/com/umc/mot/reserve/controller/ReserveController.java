package com.umc.mot.reserve.controller;

import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.hotel.service.HotelService;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import com.umc.mot.reserve.mapper.ReserveMapper;
import com.umc.mot.reserve.service.ReserveService;
import com.umc.mot.reserve.dto.ReserveRequestDto;
import com.umc.mot.reserve.dto.ReserveResponseDto;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.room.service.RoomService;
import com.umc.mot.token.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

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
        List<Reserve> reserves = reserveService.findReserve();
        List<ReserveResponseDto.Get> response=reserveMapper.ReservesResponseDto(reserves);
        return new ResponseEntity<>(response, HttpStatus.OK);
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