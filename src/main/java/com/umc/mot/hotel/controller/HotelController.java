package com.umc.mot.hotel.controller;

import com.umc.mot.hotel.dto.HotelRequestDto;
import com.umc.mot.hotel.dto.HotelResponseDto;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.hotel.mapper.HotelMapper;
import com.umc.mot.hotel.service.HotelService;
import com.umc.mot.sellMember.dto.SellMemberRequestDto;
import com.umc.mot.sellMember.dto.SellMemberResponseDto;
import com.umc.mot.sellMember.entity.SellMember;
import com.umc.mot.sellMember.mapper.SellMemberMapper;
import com.umc.mot.sellMember.service.SellMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/hotel")
@Validated
@AllArgsConstructor
public class HotelController {
    private final HotelService hotelService;
    private final HotelMapper hotelMapper;

    // Create
    @PostMapping
    public ResponseEntity postHotel(@Valid @RequestBody HotelRequestDto.Post post
            ,@RequestHeader("Authorization") String token){
        Hotel hotel = hotelService.createHotel(hotelMapper.HotelRequestDtoPostToHotel(post),token);
        HotelResponseDto.Response response=hotelMapper.HotelToHotelResponseDto(hotel);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // Read
    @GetMapping
    public ResponseEntity getHotel(@Positive @RequestParam int hotelId){
        Hotel hotel = hotelService.findHotel(hotelId);
        HotelResponseDto.Response response = hotelMapper.HotelToHotelResponseDto(hotel);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    // Update
    @PatchMapping("/{hotel-id}")
    public ResponseEntity patchHotel(@Positive @PathVariable("hotel-id") int hotelId,
                                      @RequestBody HotelRequestDto.Patch patch) {
        patch.setId(hotelId);
        Hotel hotel = hotelService.patchHotel(hotelMapper.HotelRequestDtoPatchToHotel(patch));
       HotelResponseDto.Response response =hotelMapper.HotelToHotelResponseDto(hotel);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Delete
    @DeleteMapping("/{hotel-id}")
    public ResponseEntity deleteHotel(@Positive @PathVariable("hotel-id") int hotelId) {
        hotelService.deleteHotel(hotelId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
